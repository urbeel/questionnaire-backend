package by.urbel.questionnaireportal.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FieldAnswerDto {
    private Long id;
    private Long fieldId;
    private Long questionnaireAnswerId;
    private String value;
}
