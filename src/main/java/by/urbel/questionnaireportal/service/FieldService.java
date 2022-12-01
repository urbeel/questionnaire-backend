package by.urbel.questionnaireportal.service;

import by.urbel.questionnaireportal.dto.FieldDto;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.UUID;

public interface FieldService {
    void create(FieldDto fieldDto);

    List<FieldDto> readAllByQuestionnaireId(UUID questionnaireId, Pageable pageable);

    List<FieldDto> readAllActive(UUID questionnaireId);

    void update(UUID id, FieldDto fieldDto);

    void delete(UUID id, Authentication authentication);

    long getSize(UUID questionnaireId);
}
