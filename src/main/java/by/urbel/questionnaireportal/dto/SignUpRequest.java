package by.urbel.questionnaireportal.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class SignUpRequest {
    @NotBlank(message = "Email cannot be empty.")
    @Email(message = "Invalid email.")
    @Size(max = 256, message = "Max length of email is 256 characters.")
    private String email;
    @NotBlank(message = "Password cannot be empty.")
    @Size(min = 6, max = 16, message = "The password must be between 6 and 16 characters long.")
    private String password;
    @NotBlank(message = "Confirm password cannot be empty.")
    @Size(min = 6, max = 16, message = "The confirm password must be between 6 and 16 characters long.")
    private String confirmPassword;
    @Size(max = 50, message = "Max length of firstname is 50 characters.")
    private String firstname;
    @Size(max = 50, message = "Max length of lastname is 50 characters.")
    private String lastname;
    @Size(max = 16, message = "Max length of phone number is 16 characters.")
    private String phone;
}
