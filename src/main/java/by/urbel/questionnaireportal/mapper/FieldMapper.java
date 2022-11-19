package by.urbel.questionnaireportal.mapper;

import by.urbel.questionnaireportal.dto.FieldDto;
import by.urbel.questionnaireportal.entity.Field;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface FieldMapper {
    Field dtoToField(FieldDto dto);

    FieldDto fieldToDto(Field field);

    List<FieldDto> fieldsToDto(List<Field> fields);
}
