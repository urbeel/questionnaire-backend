package by.urbel.questionnaireportal.service.impl;

import by.urbel.questionnaireportal.dto.FieldDto;
import by.urbel.questionnaireportal.entity.Field;
import by.urbel.questionnaireportal.entity.User;
import by.urbel.questionnaireportal.mapper.FieldMapper;
import by.urbel.questionnaireportal.repository.FieldRepository;
import by.urbel.questionnaireportal.repository.QuestionnaireRepository;
import by.urbel.questionnaireportal.service.FieldService;
import by.urbel.questionnaireportal.service.exceptions.AccessDeniedException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FieldServiceImpl implements FieldService {
    private final FieldRepository fieldRepository;
    private final QuestionnaireRepository questionnaireRepository;
    private final FieldMapper fieldMapper;

    @Override
    public void create(FieldDto fieldDto) {
        Field field = fieldMapper.dtoToField(fieldDto);
        if (fieldDto.getId() != null && fieldRepository.existsById(fieldDto.getId())) {
            throw new EntityExistsException(String.format("Field %d already exists.", fieldDto.getId()));
        }
        field.setQuestionnaire(questionnaireRepository.findById(fieldDto.getQuestionnaireId()).orElseThrow(() -> {
            throw new EntityNotFoundException(String.format("Questionnaire %d not found.", fieldDto.getQuestionnaireId()));
        }));
        fieldRepository.save(field);
    }

    @Override
    public List<FieldDto> readAllByQuestionnaireId(Long questionnaireId, Integer page, Integer size) {
        if (page == null || size == null) {
            return fieldMapper.fieldsToDto(fieldRepository.findAllByQuestionnaireId(questionnaireId));
        }
        Page<Field> fieldPage = fieldRepository.findAllByQuestionnaireId(questionnaireId, PageRequest.of(page, size));
        return fieldMapper.fieldsToDto(fieldPage.toList());
    }

    @Override
    public List<FieldDto> readAllActive(Long questionnaireId) {
        return fieldMapper.fieldsToDto(fieldRepository.
                findAllByQuestionnaireIdAndIsActive(questionnaireId, true));
    }

    @Override
    public void update(Long id, FieldDto fieldDto) {
        Field field = fieldRepository.findById(id).orElseThrow(() -> {
            throw new EntityNotFoundException(String.format("Field %d not found.", id));
        });
        fieldMapper.updateExisting(fieldDto, field);
        field.setId(id);
        fieldRepository.save(field);
    }

    @Override
    public void delete(Long id, Authentication auth) {
        User user = (User) auth.getPrincipal();
        if (!fieldRepository.existsByIdAndQuestionnaire(id, user.getQuestionnaire())) {
            throw new AccessDeniedException("No permission to delete this field.");
        }
        if (fieldRepository.existsById(id)) {
            fieldRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException(String.format("Field %d not found.", id));
        }
    }

    @Override
    public long getSize(Long questionnaireId) {
        return fieldRepository.countAllByQuestionnaireId(questionnaireId);
    }
}
