package by.urbel.questionnaireportal.dto;

import by.urbel.questionnaireportal.constants.Messages;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.UUID;

@Getter
@Setter
public class FieldAnswerDto {
    @Null(message = Messages.FIELD_ANSWER_ID_NOT_NULL)
    private UUID id;
    @NotNull(message = Messages.FIELD_ID_NOT_NULL)
    private UUID fieldId;
    private UUID questionnaireAnswerId;
    private String value;
}
