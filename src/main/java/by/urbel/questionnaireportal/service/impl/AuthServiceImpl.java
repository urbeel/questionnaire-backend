package by.urbel.questionnaireportal.service.impl;

import by.urbel.questionnaireportal.dto.AuthResponse;
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
import by.urbel.questionnaireportal.service.exceptions.EmailAlreadyUsedException;
import by.urbel.questionnaireportal.service.exceptions.PasswordConfirmationException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final MailService mailService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;

    public void register(SignUpRequest signUpRequest) {
        checkPasswordsEquality(signUpRequest);
        User user = userMapper.signUpRequestToUser(signUpRequest);
        checkExistence(user);
        user.setRole(UserRole.ROLE_USER);
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        Questionnaire questionnaire = new Questionnaire();
        questionnaire.setAuthor(user);
        user.setQuestionnaire(questionnaire);
        userRepository.save(user);
        mailService.sendNotificationOfSuccessfulRegistration(user.getEmail());
    }

    @Override
    public AuthResponse login(SignInRequest signInRequest) {
        User user = userRepository.findByEmail(signInRequest.getEmail()).orElseThrow(() ->
                new UsernameNotFoundException("Incorrect email or password."));
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signInRequest.getEmail(), signInRequest.getPassword()));
        String fullName = user.getFirstname() + " " + user.getLastname();
        return AuthResponse.builder()
                .jwtToken(jwtTokenUtil.generateAccessToken(signInRequest.getEmail()))
                .fullName(fullName)
                .questionnaireId(user.getQuestionnaire().getId())
                .userId(user.getId())
                .build();
    }

    public void logout() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(null);
    }

    private void checkExistence(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new EmailAlreadyUsedException("Email is already used.");
        }
    }

    private void checkPasswordsEquality(SignUpRequest signUpRequest) {
        if (!signUpRequest.getPassword().equals(signUpRequest.getConfirmPassword())) {
            throw new PasswordConfirmationException("Password and confirm password does not match.");
        }
    }
}
