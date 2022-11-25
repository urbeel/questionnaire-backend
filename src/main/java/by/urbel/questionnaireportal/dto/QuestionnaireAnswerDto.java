package by.urbel.questionnaireportal.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class QuestionnaireAnswerDto {
    private Long id;
    private Long questionnaireId;
    private List<FieldAnswerDto> fieldAnswers;
}
