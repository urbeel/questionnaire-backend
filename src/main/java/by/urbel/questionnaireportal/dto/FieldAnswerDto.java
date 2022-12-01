package by.urbel.questionnaireportal.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.UUID;

@Getter
@Setter
public class FieldAnswerDto {
    @Null(message = "Id of field answer must be null.")
    private UUID id;
    @NotNull(message = "Field id cannot be null.")
    private UUID fieldId;
    private UUID questionnaireAnswerId;
    private String value;
}
