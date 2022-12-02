package by.urbel.questionnaireportal.dto;

import by.urbel.questionnaireportal.constants.Messages;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;

@Getter
@Setter
public class ChangePasswordRequest {
    @NotNull(message = Messages.USER_ID_NOT_NULL)
    private UUID userId;
    @NotBlank(message = Messages.OLD_PASSWORD_NOT_EMPTY)
    @Size(min = 6, max = 16, message = Messages.OLD_PASSWORD_SIZE)
    private String oldPassword;
    @NotBlank(message = Messages.PASSWORD_NOT_EMPTY)
    @Size(min = 6, max = 16, message = Messages.PASSWORD_SIZE)
    private String password;
}
