package by.urbel.questionnaireportal.service.impl;

import by.urbel.questionnaireportal.dto.FieldDto;
import by.urbel.questionnaireportal.entity.Field;
import by.urbel.questionnaireportal.mapper.FieldMapper;
import by.urbel.questionnaireportal.repository.FieldRepository;
import by.urbel.questionnaireportal.repository.QuestionnaireRepository;
import by.urbel.questionnaireportal.service.FieldService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FieldServiceImpl implements FieldService {
    private final FieldRepository fieldRepository;
    private final QuestionnaireRepository questionnaireRepository;
    private final FieldMapper fieldMapper;

    @Override
    @Transactional
    public void create(FieldDto fieldDto) {
        Field field = fieldMapper.dtoToField(fieldDto);

        field.setQuestionnaire(questionnaireRepository.findById(fieldDto.getQuestionnaireId()).orElseThrow(() -> {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("Questionnaire %d not found", fieldDto.getId()));
        }));
        fieldRepository.save(field);
    }

    @Override
    public List<FieldDto> readAllByQuestionnaireId(Long questionnaireId, Integer page, Integer size) {
        if (page == null || size == null) {
            return fieldMapper.fieldsToDto(fieldRepository.findAllByQuestionnaire_Id(questionnaireId));
        }
        Page<Field> fieldPage = fieldRepository.findAllByQuestionnaire_Id(questionnaireId, PageRequest.of(page, size));
        return fieldMapper.fieldsToDto(fieldPage.toList());
    }

    @Override
    public List<FieldDto> readAllActive(Long questionnaireId) {
        return fieldMapper.fieldsToDto(fieldRepository.
                findAllByQuestionnaire_IdAndIsActive(questionnaireId, true));
    }

    @Override
    public void update(Long id, FieldDto fieldDto) {
        Field field = fieldRepository.findById(id).orElseThrow(() -> {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Field %d not found", id));
        });
        fieldMapper.updateExisting(fieldDto, field);
        field.setId(id);
        fieldRepository.save(field);
    }

    @Override
    public void delete(Long id) {
        if (fieldRepository.existsById(id)) {
            fieldRepository.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Filed %d not found", id));
        }
    }

    @Override
    public long getSize(Long questionnaireId) {
        return fieldRepository.countAllByQuestionnaire_Id(questionnaireId);
    }
}
