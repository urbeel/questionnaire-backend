package by.urbel.questionnaireportal.service;

import by.urbel.questionnaireportal.dto.QuestionnaireAnswerDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface QuestionnaireAnswerService {
    void create(QuestionnaireAnswerDto dto);

    List<QuestionnaireAnswerDto> readAllByQuestionnaireId(Long questionnaireId, Pageable pageable);

    Long getSize(Long questionnaireId);
}
