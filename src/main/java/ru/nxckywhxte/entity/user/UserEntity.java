package ru.nxckywhxte.entity.user;

import jakarta.persistence.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import ru.nxckywhxte.dto.auth.AuthenticationRequest;
import ru.nxckywhxte.dto.auth.AuthenticationResponse;
import ru.nxckywhxte.dto.auth.RegisterRequest;
import ru.nxckywhxte.entity.profile.ProfileEntity;
import ru.nxckywhxte.entity.role.RoleEntity;
import ru.nxckywhxte.entity.EntityClass;

import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;


@Table(name = "users")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class UserEntity extends EntityClass {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;
    @Column(unique = true)
    private String email;

    private String password;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinTable(name = "users_profiles",
    joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
    inverseJoinColumns = {@JoinColumn(name = "profile_id", referencedColumnName = "id")})
    private ProfileEntity profile;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "user_id",
                    referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id",
                    referencedColumnName = "id"))
    private Collection<RoleEntity> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream().map(roleEntity -> new SimpleGrantedAuthority(roleEntity.getName())).collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
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


    /**
     * Пишем реализацию регистрации (собрать это представление).
     **/
    @Override
    public AuthenticationResponse register(RegisterRequest request) {
        return null;
    }

    @Override
    public AuthenticationResponse login(AuthenticationRequest request) {
        return null;
    }

    @Override
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) {

    }

    @Override
    protected void revokeAllTokens(EntityClass entity) {

    }

    @Override
    protected void saveToken(EntityClass entity, String jwtToken) {

    }
}
