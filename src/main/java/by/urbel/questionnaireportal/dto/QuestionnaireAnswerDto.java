package by.urbel.questionnaireportal.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class QuestionnaireAnswerDto {
    @Null(message = "Id of questionnaire answer must be null.")
    private UUID id;
    @NotNull(message = "Questionnaire id cannot be null.")
    private UUID questionnaireId;
    @NotEmpty(message = "Questionnaire must have answers of fields.")
    @Valid
    private List<FieldAnswerDto> fieldAnswers;
}
