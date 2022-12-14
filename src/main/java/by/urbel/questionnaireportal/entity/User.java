package by.urbel.questionnaireportal.entity;

import by.urbel.questionnaireportal.constants.Tables;
import by.urbel.questionnaireportal.entity.enums.UserRole;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;


@Entity
@Table(name = Tables.USERS)
@Getter
@Setter
public class User extends EntityWithUuid implements UserDetails {
    @Column(length = 256, unique = true, nullable = false)
    private String email;
    @Column(length = 60, nullable = false)
    private String password;
    @Column(length = 50)
    private String firstname;
    @Column(length = 50)
    private String lastname;
    @Enumerated(EnumType.STRING)
    @Column(length = 50, nullable = false)
    private UserRole role;
    @Column(length = 16)
    private String phone;
    @OneToOne(mappedBy = "author", cascade = CascadeType.ALL)
    private Questionnaire questionnaire;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(this.role.name()));
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
