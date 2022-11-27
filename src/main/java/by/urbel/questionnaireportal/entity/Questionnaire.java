package by.urbel.questionnaireportal.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "questionnaires")
@Data
public class Questionnaire {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "questionnaires_id_seq")
    private Long id;
    @OneToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "user_id", unique = true, nullable = false)
    private User author;
    @OneToMany(mappedBy = "questionnaire")
    private List<Field> fields;
}
