package by.urbel.questionnaireportal.service;

import by.urbel.questionnaireportal.dto.QuestionnaireAnswerDto;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface QuestionnaireAnswerService {
    void create(QuestionnaireAnswerDto dto);

    List<QuestionnaireAnswerDto> readAllByQuestionnaireId(UUID questionnaireId, Pageable pageable);

    Long getSize(UUID questionnaireId);
}
