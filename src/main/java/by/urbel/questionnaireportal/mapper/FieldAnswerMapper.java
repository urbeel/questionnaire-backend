package by.urbel.questionnaireportal.mapper;

import by.urbel.questionnaireportal.dto.FieldAnswerDto;
import by.urbel.questionnaireportal.entity.FieldAnswer;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface FieldAnswerMapper {
    @Mapping(target = "fieldId", source = "field.id")
    @Mapping(target = "questionnaireAnswerId", source = "questionnaireAnswer.id")
    FieldAnswerDto fieldAnswerToDto(FieldAnswer field);

    @InheritInverseConfiguration
    @Mapping(target = "value", conditionExpression = "java(field.getValue()!=null && !field.getValue().isEmpty())")
    FieldAnswer dtoToFieldAnswer(FieldAnswerDto field);
}
