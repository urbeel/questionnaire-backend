package by.urbel.questionnaireportal.repository;

import by.urbel.questionnaireportal.entity.Field;
import by.urbel.questionnaireportal.entity.Questionnaire;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FieldRepository extends JpaRepository<Field, Long> {
    Page<Field> findAllByQuestionnaire_Id(Long questionnaireId, PageRequest pageRequest);

    List<Field> findAllByQuestionnaire_Id(Long questionnaireId);

    List<Field> findAllByQuestionnaire_IdAndIsActive(Long questionnaireId, Boolean isActive);

    Long countAllByQuestionnaire_Id(Long questionnaireId);

    Boolean existsByIdAndQuestionnaire(Long id, Questionnaire questionnaire);
}
