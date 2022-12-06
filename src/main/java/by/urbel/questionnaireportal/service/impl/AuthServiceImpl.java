package by.urbel.questionnaireportal.service.impl;

import by.urbel.questionnaireportal.constants.Mail;
import by.urbel.questionnaireportal.constants.Messages;
import by.urbel.questionnaireportal.dto.AuthResponse;
import by.urbel.questionnaireportal.dto.ChangePasswordRequest;
import by.urbel.questionnaireportal.dto.SignInRequest;
import by.urbel.questionnaireportal.dto.SignUpRequest;
import by.urbel.questionnaireportal.entity.Questionnaire;
import by.urbel.questionnaireportal.entity.User;
import by.urbel.questionnaireportal.entity.enums.UserRole;
import by.urbel.questionnaireportal.mapper.UserMapper;
import by.urbel.questionnaireportal.repository.UserRepository;
import by.urbel.questionnaireportal.security.JwtTokenUtil;
import by.urbel.questionnaireportal.service.AuthService;
import by.urbel.questionnaireportal.service.MailService;
import by.urbel.questionnaireportal.service.exceptions.ChangePasswordException;
import by.urbel.questionnaireportal.service.exceptions.EmailAlreadyUsedException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final MailService mailService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;

    @Value(value = "${client.url}")
    private String clientUrl;

    public void register(SignUpRequest signUpRequest) {
        User user = userMapper.signUpRequestToUser(signUpRequest);
        checkExistence(user);
        user.setRole(UserRole.ROLE_USER);
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        Questionnaire questionnaire = new Questionnaire();
        questionnaire.setAuthor(user);
        user.setQuestionnaire(questionnaire);
        userRepository.save(user);
        mailService.sendMessage(
                user.getEmail(),
                Mail.REGISTRATION_SUBJECT,
                Mail.REGISTRATION_TEMPLATE_NAME,
                setRegistrationSuccessVariables(signUpRequest.getFirstname())
        );
    }

    @Override
    public AuthResponse login(SignInRequest signInRequest) {
        User user = userRepository.findByEmail(signInRequest.getEmail()).orElseThrow(() ->
                new UsernameNotFoundException(Messages.INCORRECT_EMAIL_PASSWORD));
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signInRequest.getEmail(), signInRequest.getPassword()));
        return AuthResponse.builder()
                .jwtToken(jwtTokenUtil.generateAccessToken(signInRequest.getEmail()))
                .fullName(generateFullName(user.getFirstname(), user.getLastname()))
                .questionnaireId(user.getQuestionnaire().getId())
                .userId(user.getId())
                .build();
    }

    @Override
    public void changePassword(ChangePasswordRequest dto) {
        User user = userRepository.findById(dto.getUserId()).orElseThrow(() -> {
            throw new EntityNotFoundException(Messages.USER_NOT_FOUND);
        });
        if (!passwordEncoder.matches(dto.getOldPassword(), user.getPassword())) {
            throw new ChangePasswordException(Messages.INCORRECT_OLD_PASSWORD);
        }
        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
            userRepository.save(user);
            mailService.sendMessage(
                    user.getEmail(),
                    Mail.CHANGE_PASSWORD_SUBJECT,
                    Mail.CHANGE_PASSWORD_TEMPLATE_NAME,
                    setChangedPasswordVariables()
            );
        } else {
            throw new ChangePasswordException(Messages.PASSWORDS_MUST_BE_DIFFERENT);
        }
    }

    private void checkExistence(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new EmailAlreadyUsedException(Messages.EMAIL_USED);
        }
    }

    private String generateFullName(String firstname, String lastname) {
        StringBuilder fullName = new StringBuilder();
        if (firstname != null) {
            fullName.append(firstname);
            fullName.append(" ");
        }
        if (lastname != null) {
            fullName.append(lastname);
        }
        return !fullName.isEmpty() ? fullName.toString().trim() : null;
    }

    private Map<String, Object> setRegistrationSuccessVariables(String firstname) {
        Map<String, Object> map = new HashMap<>();
        map.put("appLink", clientUrl);
        map.put("recipientName", firstname != null && !firstname.isBlank() ? firstname : "User");
        return map;
    }

    private Map<String, Object> setChangedPasswordVariables() {
        Map<String, Object> map = new HashMap<>();
        map.put("contactEmail", Mail.CONTACT_EMAIL);
        return map;
    }
}
