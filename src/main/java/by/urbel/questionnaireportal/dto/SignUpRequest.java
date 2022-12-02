package by.urbel.questionnaireportal.dto;

import by.urbel.questionnaireportal.constants.Messages;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class SignUpRequest {
    @NotBlank(message = Messages.EMAIL_NOT_EMPTY)
    @Email(message = Messages.INVALID_EMAIL)
    @Size(max = 256, message = Messages.EMAIL_SIZE)
    private String email;
    @NotBlank(message = Messages.PASSWORD_NOT_EMPTY)
    @Size(min = 6, max = 16, message = Messages.PASSWORD_SIZE)
    private String password;
    @Size(max = 50, message = Messages.FIRSTNAME_SIZE)
    private String firstname;
    @Size(max = 50, message = Messages.LASTNAME_SIZE)
    private String lastname;
    @Size(max = 16, message = Messages.PHONE_SIZE)
    private String phone;
}
