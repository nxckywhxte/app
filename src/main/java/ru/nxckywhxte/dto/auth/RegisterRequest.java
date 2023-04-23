package ru.nxckywhxte.dto.auth;

import lombok.*;
import ru.nxckywhxte.entity.role.RoleEntity;

import java.util.Collection;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String email;
    private String password;
    private String role;
    private Collection<RoleEntity> roles;
}
