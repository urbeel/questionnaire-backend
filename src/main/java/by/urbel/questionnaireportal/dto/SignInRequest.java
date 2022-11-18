package by.urbel.questionnaireportal.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class SignInRequest {
    @NotBlank(message = "Email cannot be empty.")
    @Email(message = "Invalid email")
    @Size(max = 256, message = "Max length of email is 256 characters.")
    private String email;
    @NotBlank(message = "Password cannot be empty.")
    @Size(min = 6, max = 16, message = "The password must be between 6 and 16 characters long.")
    private String password;
}
