package by.urbel.questionnaireportal.dto;

import by.urbel.questionnaireportal.constants.Messages;
import by.urbel.questionnaireportal.customvalidation.ValueOfEnum;
import by.urbel.questionnaireportal.entity.enums.FieldType;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class FieldDto {
    @Null(groups = {New.class}, message = Messages.FIELD_ID_NULL)
    private UUID id;
    @NotBlank(groups = {New.class, Update.class}, message = Messages.LABEL_NOT_EMPTY)
    @Size(groups = {New.class, Update.class}, max = 100, message = Messages.LABEL_SIZE)
    private String label;
    @NotBlank(groups = {New.class, Update.class}, message = Messages.FIELD_TYPE_NOT_EMPTY)
    @Size(groups = {New.class, Update.class}, max = 50, message = Messages.FIELD_TYPE_SIZE)
    @ValueOfEnum(groups = {New.class, Update.class}, enumClass = FieldType.class)
    private String type;
    private List<String> options;
    @NotNull(groups = {New.class, Update.class}, message = Messages.REQUIRED_NOT_NULL)
    private Boolean isRequired;
    @NotNull(groups = {New.class, Update.class}, message = Messages.ACTIVE_NOT_NULL)
    private Boolean isActive;
    @NotNull(groups = {New.class, Update.class}, message = Messages.Q_ID_NOT_NULL)
    private UUID questionnaireId;

    public interface New {
    }

    public interface Update {
    }
}
