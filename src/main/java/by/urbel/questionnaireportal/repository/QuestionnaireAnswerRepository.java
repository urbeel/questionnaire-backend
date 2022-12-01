package by.urbel.questionnaireportal.repository;

import by.urbel.questionnaireportal.entity.QuestionnaireAnswer;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface QuestionnaireAnswerRepository extends JpaRepository<QuestionnaireAnswer, UUID> {
    List<QuestionnaireAnswer> findAllByQuestionnaireId(UUID questionnaireId, Pageable pageable);

    Long countAllByQuestionnaireId(UUID questionnaireId);
}
