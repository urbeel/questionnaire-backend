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
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class QuestionnaireAnswerServiceImpl implements QuestionnaireAnswerService {
    private final QuestionnaireAnswerMapper mapper;
    private final QuestionnaireAnswerRepository questionnaireAnswerRepository;
    private final QuestionnaireRepository questionnaireRepository;
    private final FieldRepository fieldRepository;

    @Override
    public void create(QuestionnaireAnswerDto dto) {
        if (dto.getId() != null && questionnaireAnswerRepository.existsById(dto.getId())) {
            throw new EntityExistsException(String.format("Answer %d on questionnaire already exists.", dto.getId()));
        }
        QuestionnaireAnswer questionnaireAnswer = mapper.dtoToQuestionnaireAnswer(dto);
        Questionnaire questionnaire = questionnaireRepository.findById(dto.getQuestionnaireId()).orElseThrow(() -> {
            throw new EntityNotFoundException(String.format("Questionnaire %d not found.", dto.getQuestionnaireId()));
        });
        questionnaireAnswer.getFieldAnswers().forEach(fieldAnswer -> {
            fieldAnswer.setQuestionnaireAnswer(questionnaireAnswer);
            Field field = fieldRepository.findById(fieldAnswer.getField().getId()).orElseThrow(() -> {
                throw new EntityNotFoundException(String.format("Field %d not found.", fieldAnswer.getField().getId()));
            });
            fieldAnswer.setField(field);
        });
        questionnaireAnswer.setQuestionnaire(questionnaire);
        questionnaireAnswerRepository.save(questionnaireAnswer);
    }

    @Override
    public List<QuestionnaireAnswerDto> readAllByQuestionnaireId(UUID questionnaireId, Pageable pageable) {
        List<QuestionnaireAnswer> questionnaireAnswers =
                questionnaireAnswerRepository.findAllByQuestionnaireId(questionnaireId, pageable);
        return mapper.questionnaireAnswersToDto(questionnaireAnswers);
    }

    @Override
    public Long getSize(UUID questionnaireId) {
        return questionnaireAnswerRepository.countAllByQuestionnaireId(questionnaireId);
    }
}
