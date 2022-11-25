package by.urbel.questionnaireportal.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "questionnaire_answers")
@Data
public class QuestionnaireAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "questionnaire_id")
    private Questionnaire questionnaire;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "questionnaireAnswer")
    private List<FieldAnswer> fieldAnswers;
}
