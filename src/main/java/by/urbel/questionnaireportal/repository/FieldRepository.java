package by.urbel.questionnaireportal.repository;

import by.urbel.questionnaireportal.entity.Field;
import by.urbel.questionnaireportal.entity.Questionnaire;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface FieldRepository extends JpaRepository<Field, UUID> {
    Page<Field> findAllByQuestionnaireId(UUID questionnaireId, Pageable pageable);

    List<Field> findAllByQuestionnaireIdAndIsActive(UUID questionnaireId, Boolean isActive);

    Long countAllByQuestionnaireId(UUID questionnaireId);

    Boolean existsByIdAndQuestionnaire(UUID id, Questionnaire questionnaire);
}
