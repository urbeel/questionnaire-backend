package by.urbel.questionnaireportal.controller;

import by.urbel.questionnaireportal.constants.Roles;
import by.urbel.questionnaireportal.constants.Routes;
import by.urbel.questionnaireportal.dto.AuthResponse;
import by.urbel.questionnaireportal.dto.ChangePasswordRequest;
import by.urbel.questionnaireportal.dto.SignInRequest;
import by.urbel.questionnaireportal.dto.SignUpRequest;
import by.urbel.questionnaireportal.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(Routes.AUTH)
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping(Routes.SIGNUP)
    public void register(@Valid @RequestBody SignUpRequest signUpRequest) {
        authService.register(signUpRequest);
    }

    @PostMapping(Routes.LOGIN)
    public AuthResponse authenticate(@Valid @RequestBody SignInRequest signInRequest) {
        return authService.login(signInRequest);
    }

    @PatchMapping(Routes.CHANGE_PASSWORD)
    @PreAuthorize(Roles.USER + " and #dto.userId==principal.id")
    public void changePassword(@Valid @RequestBody ChangePasswordRequest dto) {
        authService.changePassword(dto);
    }
}
