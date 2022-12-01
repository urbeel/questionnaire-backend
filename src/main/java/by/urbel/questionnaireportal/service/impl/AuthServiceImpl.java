package by.urbel.questionnaireportal.service.impl;

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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final MailService mailService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;

    private static final String REGISTRATION_MESSAGE =
            "Thank you for registration! Now you can create your custom questionnaire.";
    private static final String CHANGE_PASSWORD_MESSAGE =
            "Your password was changed.";
    private static final String REGISTRATION_SUBJECT =
            "Successful registration";
    private static final String CHANGE_PASSWORD_SUBJECT =
            "Changing your account password";

    public void register(SignUpRequest signUpRequest) {
        User user = userMapper.signUpRequestToUser(signUpRequest);
        checkExistence(user);
        user.setRole(UserRole.ROLE_USER);
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        Questionnaire questionnaire = new Questionnaire();
        questionnaire.setAuthor(user);
        user.setQuestionnaire(questionnaire);
        userRepository.save(user);
        mailService.sendMessage(user.getEmail(), REGISTRATION_SUBJECT, REGISTRATION_MESSAGE);
    }

    @Override
    public AuthResponse login(SignInRequest signInRequest) {
        User user = userRepository.findByEmail(signInRequest.getEmail()).orElseThrow(() ->
                new UsernameNotFoundException("Incorrect email or password."));
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
            throw new EntityNotFoundException(String.format("User %d not found.", dto.getUserId()));
        });
        if (!passwordEncoder.matches(dto.getOldPassword(), user.getPassword())) {
            throw new ChangePasswordException("Incorrect old password.");
        }
        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
            userRepository.save(user);
            mailService.sendMessage(user.getEmail(), CHANGE_PASSWORD_SUBJECT, CHANGE_PASSWORD_MESSAGE);
        } else {
            throw new ChangePasswordException("New and old passwords must be different.");
        }
    }

    private void checkExistence(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new EmailAlreadyUsedException("Email is already used.");
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
}
