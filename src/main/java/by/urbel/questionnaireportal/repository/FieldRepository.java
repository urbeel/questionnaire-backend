package by.urbel.questionnaireportal.repository;

import by.urbel.questionnaireportal.entity.Field;
import by.urbel.questionnaireportal.entity.Questionnaire;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FieldRepository extends JpaRepository<Field, Long> {
    Page<Field> findAllByQuestionnaireId(Long questionnaireId, Pageable pageable);

    List<Field> findAllByQuestionnaireIdAndIsActive(Long questionnaireId, Boolean isActive);

    Long countAllByQuestionnaireId(Long questionnaireId);

    Boolean existsByIdAndQuestionnaire(Long id, Questionnaire questionnaire);
}
