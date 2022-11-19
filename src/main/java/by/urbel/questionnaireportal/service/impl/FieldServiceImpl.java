package by.urbel.questionnaireportal.service.impl;

import by.urbel.questionnaireportal.dto.FieldDto;
import by.urbel.questionnaireportal.mapper.FieldMapper;
import by.urbel.questionnaireportal.repository.FieldRepository;
import by.urbel.questionnaireportal.service.FieldService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FieldServiceImpl implements FieldService {
    private final FieldRepository fieldRepository;
    private final FieldMapper fieldMapper;

    @Override
    public void create(FieldDto fieldDto) {
        fieldRepository.save(fieldMapper.dtoToField(fieldDto));
    }

    @Override
    public List<FieldDto> readAll() {
        return fieldMapper.fieldsToDto(fieldRepository.findAll());
    }
}
