package by.urbel.questionnaireportal.controller;

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
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public void register(@Valid @RequestBody SignUpRequest signUpRequest) {
        authService.register(signUpRequest);
    }

    @PostMapping("/login")
    public AuthResponse authenticate(@Valid @RequestBody SignInRequest signInRequest) {
        return authService.login(signInRequest);
    }

    @PatchMapping("/password")
    @PreAuthorize("hasAuthority('ROLE_USER') and #dto.userId==principal.id")
    public void changePassword(@Valid @RequestBody ChangePasswordRequest dto) {
        authService.changePassword(dto);
    }
}
