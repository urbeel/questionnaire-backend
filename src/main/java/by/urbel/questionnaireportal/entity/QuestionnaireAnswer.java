package by.urbel.questionnaireportal.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "questionnaire_answers")
@Data
public class QuestionnaireAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "questionnaire_answers_id_seq")
    private Long id;
    @OneToOne
    @JoinColumn(name = "questionnaire_id", nullable = false)
    private Questionnaire questionnaire;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "questionnaireAnswer")
    private List<FieldAnswer> fieldAnswers;
}
