package by.urbel.questionnaireportal.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FieldDto {
    private Long id;
    private String label;
    private String type;
    private List<String> options;
    private Boolean isRequired;
    private Boolean isActive;
    private Long questionnaireId;
}
