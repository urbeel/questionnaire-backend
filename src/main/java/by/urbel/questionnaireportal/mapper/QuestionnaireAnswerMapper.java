package by.urbel.questionnaireportal.mapper;

import by.urbel.questionnaireportal.dto.QuestionnaireAnswerDto;
import by.urbel.questionnaireportal.entity.QuestionnaireAnswer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        uses = FieldAnswerMapper.class)
public interface QuestionnaireAnswerMapper {
    @Mapping(target = "questionnaireId", source = "questionnaire.id")
    QuestionnaireAnswerDto questionnaireAnswerToDto(QuestionnaireAnswer questionnaireAnswer);

    List<QuestionnaireAnswerDto> questionnaireAnswersToDto(List<QuestionnaireAnswer> questionnaireAnswers);

    QuestionnaireAnswer dtoToQuestionnaireAnswer(QuestionnaireAnswerDto dto);
}
