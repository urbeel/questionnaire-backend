package by.urbel.questionnaireportal.entity;

import by.urbel.questionnaireportal.constants.Tables;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = Tables.QUESTIONNAIRE_ANSWERS)
@Getter
@Setter
public class QuestionnaireAnswer extends EntityWithUuid {
    @OneToOne
    @JoinColumn(name = Tables.Fields.QUESTIONNAIRE_ID, nullable = false)
    private Questionnaire questionnaire;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "questionnaireAnswer")
    private List<FieldAnswer> fieldAnswers;
}
