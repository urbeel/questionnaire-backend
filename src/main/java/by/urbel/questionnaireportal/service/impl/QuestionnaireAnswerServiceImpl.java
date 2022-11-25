package by.urbel.questionnaireportal.service.impl;

import by.urbel.questionnaireportal.dto.QuestionnaireAnswerDto;
import by.urbel.questionnaireportal.entity.Field;
import by.urbel.questionnaireportal.entity.Questionnaire;
import by.urbel.questionnaireportal.entity.QuestionnaireAnswer;
import by.urbel.questionnaireportal.mapper.QuestionnaireAnswerMapper;
import by.urbel.questionnaireportal.repository.FieldRepository;
import by.urbel.questionnaireportal.repository.QuestionnaireAnswerRepository;
import by.urbel.questionnaireportal.repository.QuestionnaireRepository;
import by.urbel.questionnaireportal.service.QuestionnaireAnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionnaireAnswerServiceImpl implements QuestionnaireAnswerService {
    private final QuestionnaireAnswerMapper mapper;
    private final QuestionnaireAnswerRepository questionnaireAnswerRepository;
    private final QuestionnaireRepository questionnaireRepository;
    private final FieldRepository fieldRepository;

    @Override
    public void create(QuestionnaireAnswerDto dto) {
        QuestionnaireAnswer questionnaireAnswer = mapper.dtoToQuestionnaireAnswer(dto);
        Questionnaire questionnaire = questionnaireRepository.findById(dto.getQuestionnaireId()).orElseThrow(() -> {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Questionnaire not found.");
        });
        questionnaireAnswer.getFieldAnswers().forEach(fieldAnswer -> {
            fieldAnswer.setQuestionnaireAnswer(questionnaireAnswer);
            Field field = fieldRepository.findById(fieldAnswer.getField().getId()).orElseThrow(() -> {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Field not found.");
            });
            fieldAnswer.setField(field);
        });
        questionnaireAnswer.setQuestionnaire(questionnaire);
        questionnaireAnswerRepository.save(questionnaireAnswer);
    }

    @Override
    public List<QuestionnaireAnswerDto> readAllByQuestionnaireId(Long questionnaireId, Integer page, Integer size) {
        List<QuestionnaireAnswer> questionnaireAnswers =
                questionnaireAnswerRepository.findAllByQuestionnaire_Id(questionnaireId, PageRequest.of(page, size));
        return mapper.questionnaireAnswersToDto(questionnaireAnswers);
    }

    @Override
    public Long getSize(Long questionnaireId) {
        return questionnaireAnswerRepository.countAllByQuestionnaire_Id(questionnaireId);
    }
}
