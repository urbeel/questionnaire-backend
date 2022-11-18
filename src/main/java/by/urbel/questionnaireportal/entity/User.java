package by.urbel.questionnaireportal.entity;

import by.urbel.questionnaireportal.entity.enums.UserRole;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Table(name = "users")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 256, unique = true, nullable = false)
    private String email;
    @Column(length = 60, nullable = false)
    private String password;
    @Column(length = 50)
    private String firstname;
    @Column(length = 50)
    private String lastname;
    @Enumerated(EnumType.STRING)
    private UserRole role;
    @Column(length = 16)
    private String phone;
}
