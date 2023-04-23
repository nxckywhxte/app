package ru.nxckywhxte.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.nxckywhxte.entity.profile.ProfileEntity;
import ru.nxckywhxte.entity.role.RoleEntity;

import java.util.Collection;
import java.util.UUID;

public class UserResponse {
    @JsonProperty("id")
    private UUID id;

    @JsonProperty("email")
    private String email;

    @JsonProperty("profile")
    private ProfileEntity profile;

    @JsonProperty("roles")
    private Collection<RoleEntity> roles;
}
