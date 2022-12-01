package by.urbel.questionnaireportal.entity;

import by.urbel.questionnaireportal.entity.enums.FieldType;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@TypeDefs({
        @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
})
@Entity
@Table(name = "fields")
@Data
public class Field {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID id;
    @Column(nullable = false)
    private String label;
    @Enumerated(EnumType.STRING)
    @Column(length = 50, nullable = false)
    private FieldType type;
    @Type(type = "jsonb")
    @Column(columnDefinition = "json")
    private List<String> options;
    @Column(nullable = false)
    private Boolean isRequired;
    @Column(nullable = false)
    private Boolean isActive;
    @ManyToOne(optional = false)
    private Questionnaire questionnaire;
}
