package by.urbel.questionnaireportal.entity;

import by.urbel.questionnaireportal.constants.Tables;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = Tables.QUESTIONNAIRES)
@Getter
@Setter
public class Questionnaire extends EntityWithUuid {
    @OneToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = Tables.Fields.USER_ID, unique = true, nullable = false)
    private User author;
    @OneToMany(mappedBy = "questionnaire")
    private List<Field> fields;
}
