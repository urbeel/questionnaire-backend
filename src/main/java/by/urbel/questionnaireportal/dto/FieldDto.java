package by.urbel.questionnaireportal.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
public class FieldDto {
    @Null(groups = {New.class}, message = "Field id must be null.")
    private Long id;
    @NotBlank(groups = {New.class, Update.class}, message = "Label cannot be empty.")
    @Size(groups = {New.class, Update.class}, max = 255, message = "Max length of label is 255 characters.")
    private String label;
    @NotBlank(groups = {New.class, Update.class}, message = "Type cannot be empty.")
    @Size(groups = {New.class, Update.class}, max = 255, message = "Max length of type is 255 characters.")
    private String type;
    private List<String> options;
    @NotNull(groups = {New.class, Update.class}, message = "IsRequired cannot be null.")
    private Boolean isRequired;
    @NotNull(groups = {New.class, Update.class}, message = "IsActive cannot be null.")
    private Boolean isActive;
    @NotNull(groups = {New.class, Update.class}, message = "Questionnaire id cannot be null.")
    private Long questionnaireId;

    public interface New {
    }

    public interface Update {
    }
}
