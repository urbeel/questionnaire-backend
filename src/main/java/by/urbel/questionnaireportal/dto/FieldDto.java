package by.urbel.questionnaireportal.dto;

import by.urbel.questionnaireportal.entity.enums.FieldType;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FieldDto {
    private Long id;
    private String label;
    private FieldType type;
    private List<String> options;
    private Boolean isRequired;
    private Boolean isActive;
}
