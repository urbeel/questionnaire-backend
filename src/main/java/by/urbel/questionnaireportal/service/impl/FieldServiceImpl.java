package by.urbel.questionnaireportal.service.impl;

import by.urbel.questionnaireportal.constants.Messages;
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
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;

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
            throw new EntityExistsException(Messages.FIELD_EXISTS);
        }
        field.setQuestionnaire(questionnaireRepository.findById(fieldDto.getQuestionnaireId()).orElseThrow(() -> {
            throw new EntityNotFoundException(Messages.Q_NOT_FOUND);
        }));
        fieldRepository.save(field);
    }

    @Override
    public List<FieldDto> readAllByQuestionnaireId(UUID questionnaireId, Pageable pageable) {
        Page<Field> fieldPage = fieldRepository.findAllByQuestionnaireId(questionnaireId, pageable);
        return fieldMapper.fieldsToDto(fieldPage.toList());
    }

    @Override
    public List<FieldDto> readAllActive(UUID questionnaireId) {
        return fieldMapper.fieldsToDto(fieldRepository.
                findAllByQuestionnaireIdAndIsActive(questionnaireId, true));
    }

    @Override
    public void update(UUID id, FieldDto fieldDto) {
        Field field = fieldRepository.findById(id).orElseThrow(() -> {
            throw new EntityNotFoundException(Messages.FIELD_NOT_FOUND);
        });
        fieldMapper.updateExisting(fieldDto, field);
        field.setId(id);
        fieldRepository.save(field);
    }

    @Override
    public void delete(UUID id, Authentication auth) {
        User user = (User) auth.getPrincipal();
        if (!fieldRepository.existsByIdAndQuestionnaire(id, user.getQuestionnaire())) {
            throw new AccessDeniedException("");
        }
        if (fieldRepository.existsById(id)) {
            fieldRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException(Messages.NO_PERMISSIONS_DELETE_FIELD);
        }
    }

    @Override
    public long getSize(UUID questionnaireId) {
        return fieldRepository.countAllByQuestionnaireId(questionnaireId);
    }
}
