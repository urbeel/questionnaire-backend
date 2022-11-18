package by.urbel.questionnaireportal.service.impl;

import by.urbel.questionnaireportal.dto.AuthResponse;
import by.urbel.questionnaireportal.dto.SignInRequest;
import by.urbel.questionnaireportal.dto.SignUpRequest;
import by.urbel.questionnaireportal.entity.User;
import by.urbel.questionnaireportal.entity.enums.UserRole;
import by.urbel.questionnaireportal.mapper.UserMapper;
import by.urbel.questionnaireportal.repository.UserRepository;
import by.urbel.questionnaireportal.security.JwtTokenUtil;
import by.urbel.questionnaireportal.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;

    public void register(SignUpRequest signUpRequest) {
        checkPasswordsEquality(signUpRequest);
        User user = userMapper.signUpRequestToUser(signUpRequest);
        checkExistence(user);
        user.setRole(UserRole.ROLE_USER);
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        userRepository.save(user);
    }

    @Override
    public AuthResponse login(SignInRequest signInRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signInRequest.getEmail(), signInRequest.getPassword()));
        return new AuthResponse(jwtTokenUtil.generateAccessToken(signInRequest.getEmail()));
    }

    public void logout() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(null);
    }

    private void checkExistence(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email is already used.");
        }
    }

    private void checkPasswordsEquality(SignUpRequest signUpRequest) {
        if (!signUpRequest.getPassword().equals(signUpRequest.getConfirmPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password and confirm password is not equal.");
        }
    }
}
