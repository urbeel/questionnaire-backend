package by.urbel.questionnaireportal.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;

@Getter
@Setter
public class ChangePasswordRequest {
    @NotNull(message = "User id cannot be null.")
    private UUID userId;
    @NotEmpty(message = "Old password cannot be empty.")
    @Size(min = 6, max = 16, message = "Old password must be between 6 and 16 characters long.")
    private String oldPassword;
    @NotBlank(message = "Password cannot be empty.")
    @Size(min = 6, max = 16, message = "Password must be between 6 and 16 characters long.")
    private String password;
}
