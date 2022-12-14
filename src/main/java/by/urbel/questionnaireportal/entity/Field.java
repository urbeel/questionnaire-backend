package by.urbel.questionnaireportal.entity;

import by.urbel.questionnaireportal.constants.PostgresTypes;
import by.urbel.questionnaireportal.constants.Tables;
import by.urbel.questionnaireportal.entity.enums.FieldType;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import java.util.List;

@TypeDefs({
        @TypeDef(name = PostgresTypes.JSONB, typeClass = JsonBinaryType.class)
})
@Entity
@Table(name = Tables.FIELDS)
@Getter
@Setter
public class Field extends EntityWithUuid {
    @Column(nullable = false)
    private String label;
    @Enumerated(EnumType.STRING)
    @Column(length = 50, nullable = false)
    private FieldType type;
    @Type(type = PostgresTypes.JSONB)
    @Column(columnDefinition = PostgresTypes.JSON)
    private List<String> options;
    @Column(nullable = false)
    private Boolean isRequired;
    @Column(nullable = false)
    private Boolean isActive;
    @ManyToOne(optional = false)
    private Questionnaire questionnaire;
    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "field")
    private List<FieldAnswer> fieldAnswers;
}
