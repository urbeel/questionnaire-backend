package by.urbel.questionnaireportal.repository;

import by.urbel.questionnaireportal.entity.QuestionnaireAnswer;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionnaireAnswerRepository extends JpaRepository<QuestionnaireAnswer, Long> {
    List<QuestionnaireAnswer> findAllByQuestionnaireId(Long questionnaireId, PageRequest pageRequest);

    Long countAllByQuestionnaireId(Long questionnaireId);
}
