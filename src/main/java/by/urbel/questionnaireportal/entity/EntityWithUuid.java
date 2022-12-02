package by.urbel.questionnaireportal.entity;

import by.urbel.questionnaireportal.constants.UUIDGeneration;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.UUID;

@Getter
@Setter
@MappedSuperclass
public abstract class EntityWithUuid {
    @Id
    @GeneratedValue(generator = UUIDGeneration.UUID_GENERATOR)
    @GenericGenerator(
            name = UUIDGeneration.UUID_GENERATOR,
            strategy = UUIDGeneration.GENERATOR_CLASS_NAME
    )
    private UUID id;
}
