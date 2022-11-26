package by.urbel.questionnaireportal.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

@Getter
@Setter
public class FieldAnswerDto {
    @Null(message = "Id of field answer must be null.")
    private Long id;
    @NotNull(message = "Field id cannot be null.")
    private Long fieldId;
    @NotNull(message = "Id of questionnaire answer cannot be null.")
    private Long questionnaireAnswerId;
    private String value;
}
