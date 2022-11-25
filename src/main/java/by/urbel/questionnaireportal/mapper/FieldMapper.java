package by.urbel.questionnaireportal.mapper;

import by.urbel.questionnaireportal.dto.FieldDto;
import by.urbel.questionnaireportal.entity.Field;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface FieldMapper {
    Field dtoToField(FieldDto dto);

    @Mapping(target = "questionnaireId", source = "questionnaire.id")
    @Mapping(target = "type", source = "type.htmlName")
    FieldDto fieldToDto(Field field);

    List<FieldDto> fieldsToDto(List<Field> fields);

    @InheritConfiguration
    void updateExisting(FieldDto fieldDto, @MappingTarget Field field);
}
