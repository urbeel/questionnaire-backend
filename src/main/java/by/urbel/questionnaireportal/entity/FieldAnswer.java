package by.urbel.questionnaireportal.entity;

import by.urbel.questionnaireportal.constants.PostgresTypes;
import by.urbel.questionnaireportal.constants.Tables;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = Tables.FIELD_ANSWERS)
@Getter
@Setter
public class FieldAnswer extends EntityWithUuid {
    @ManyToOne(optional = false)
    @JoinColumn(name = Tables.Fields.FIELD_ID)
    private Field field;
    @ManyToOne(optional = false)
    private QuestionnaireAnswer questionnaireAnswer;
    @Type(type = PostgresTypes.TEXT)
    private String value;
}
