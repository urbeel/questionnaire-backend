package by.urbel.questionnaireportal.service;

import by.urbel.questionnaireportal.dto.AuthResponse;
import by.urbel.questionnaireportal.dto.SignInRequest;
import by.urbel.questionnaireportal.dto.SignUpRequest;

public interface AuthService {
    void register(SignUpRequest signUpRequest);

    AuthResponse login(SignInRequest signInRequest);

    void logout();
}
