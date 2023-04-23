package ru.nxckywhxte.entity;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.userdetails.UserDetails;
import ru.nxckywhxte.dto.auth.AuthenticationRequest;
import ru.nxckywhxte.dto.auth.AuthenticationResponse;
import ru.nxckywhxte.dto.auth.RegisterRequest;

public abstract class EntityClass implements UserDetails {

    abstract public AuthenticationResponse register(RegisterRequest request);

    abstract public AuthenticationResponse login(AuthenticationRequest request);

    abstract public void refreshToken(HttpServletRequest request, HttpServletResponse response);

    abstract protected void revokeAllTokens(EntityClass entity);

    abstract protected void saveToken(EntityClass entity, String jwtToken);
}
