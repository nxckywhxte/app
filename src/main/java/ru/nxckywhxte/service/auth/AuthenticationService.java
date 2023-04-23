package ru.nxckywhxte.service.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nxckywhxte.dto.auth.AuthenticationRequest;
import ru.nxckywhxte.dto.auth.AuthenticationResponse;
import ru.nxckywhxte.dto.auth.RegisterRequest;
import ru.nxckywhxte.entity.EntityClass;
import ru.nxckywhxte.entity.EntityFactory;


@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final EntityFactory entityFactory;
    private EntityClass entity;

    public AuthenticationResponse register(RegisterRequest request) {
        entity = entityFactory.create(request.getRole());
        return entity.register(request);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        entity = entityFactory.create(request.getRole());
        return entity.login(request);
    }

    public void refreshToken(HttpServletRequest request, HttpServletResponse response) {
        entity.refreshToken(request, response);
    }
}
