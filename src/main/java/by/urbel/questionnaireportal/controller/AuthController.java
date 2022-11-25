package by.urbel.questionnaireportal.controller;

import by.urbel.questionnaireportal.dto.AuthResponse;
import by.urbel.questionnaireportal.dto.SignInRequest;
import by.urbel.questionnaireportal.dto.SignUpRequest;
import by.urbel.questionnaireportal.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public void register(@Valid @RequestBody SignUpRequest signUpRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, bindingResult.getFieldError().getDefaultMessage());
        }
        authService.register(signUpRequest);
    }

    @PostMapping("/login")
    public AuthResponse authenticate(@Valid @RequestBody SignInRequest signInRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, bindingResult.getFieldError().getDefaultMessage());
        }
        return authService.login(signInRequest);
    }

    @GetMapping("/logout")
    public void logout() {
        authService.logout();
    }
}
