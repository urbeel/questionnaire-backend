package by.urbel.questionnaireportal.service;

import by.urbel.questionnaireportal.dto.FieldDto;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface FieldService {
    void create(FieldDto fieldDto);

    List<FieldDto> readAllByQuestionnaireId(Long questionnaireId, Integer page, Integer size);

    List<FieldDto> readAllActive(Long questionnaireId);

    void update(Long id, FieldDto fieldDto);

    void delete(Long id, Authentication authentication);

    long getSize(Long questionnaireId);
}
