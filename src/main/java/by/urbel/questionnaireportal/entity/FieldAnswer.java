package by.urbel.questionnaireportal.entity;

import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
@Table(name = "field_answers")
@Data
public class FieldAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "field_answers_id_seq")
    private Long id;
    @ManyToOne(optional = false)
    @JoinColumn(name = "field_id")
    private Field field;
    @ManyToOne(optional = false)
    private QuestionnaireAnswer questionnaireAnswer;
    @Type(type = "text")
    private String value;
}
