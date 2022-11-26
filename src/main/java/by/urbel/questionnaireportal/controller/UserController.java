package by.urbel.questionnaireportal.controller;

import by.urbel.questionnaireportal.dto.UserDto;
import by.urbel.questionnaireportal.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PutMapping("/{userId}")
    @PreAuthorize("#userId==principal.id")
    public void updateUser(@PathVariable Long userId, @Validated @RequestBody UserDto userDto) {
        userService.update(userId, userDto);
    }
}
