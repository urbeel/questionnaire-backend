package by.urbel.questionnaireportal.service;

import by.urbel.questionnaireportal.dto.QuestionnaireAnswerDto;

import java.util.List;

public interface QuestionnaireAnswerService {
    void create(QuestionnaireAnswerDto dto);

    List<QuestionnaireAnswerDto> readAllByQuestionnaireId(Long questionnaireId, Integer page, Integer size);

    Long getSize(Long questionnaireId);
}
