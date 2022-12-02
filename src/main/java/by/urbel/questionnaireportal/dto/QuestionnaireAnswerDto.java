package by.urbel.questionnaireportal.dto;

import by.urbel.questionnaireportal.constants.Messages;
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
    @Null(message = Messages.Q_ANSWER_ID_NOT_NULL)
    private UUID id;
    @NotNull(message = Messages.Q_ID_NOT_NULL)
    private UUID questionnaireId;
    @NotEmpty(message = Messages.FIELD_ANSWERS_NOT_EMPTY)
    @Valid
    private List<FieldAnswerDto> fieldAnswers;
}
