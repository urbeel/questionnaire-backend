package by.urbel.questionnaireportal.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "questionnaire_answers")
@Data
public class QuestionnaireAnswer {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID id;
    @OneToOne
    @JoinColumn(name = "questionnaire_id", nullable = false)
    private Questionnaire questionnaire;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "questionnaireAnswer")
    private List<FieldAnswer> fieldAnswers;
}
