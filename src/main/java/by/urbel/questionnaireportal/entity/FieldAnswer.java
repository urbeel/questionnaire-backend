package by.urbel.questionnaireportal.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "field_answers")
@Data
public class FieldAnswer {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID id;
    @ManyToOne(optional = false)
    @JoinColumn(name = "field_id")
    private Field field;
    @ManyToOne(optional = false)
    private QuestionnaireAnswer questionnaireAnswer;
    @Type(type = "text")
    private String value;
}
