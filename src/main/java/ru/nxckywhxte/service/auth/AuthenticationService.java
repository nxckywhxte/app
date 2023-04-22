package ru.nxckywhxte.service.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.nxckywhxte.dto.auth.AuthenticationRequest;
import ru.nxckywhxte.dto.auth.AuthenticationResponse;
import ru.nxckywhxte.dto.auth.RegisterRequest;
import ru.nxckywhxte.entity.admin.AdminEntity;
import ru.nxckywhxte.entity.token.TokenEntity;
import ru.nxckywhxte.entity.token.TokenType;
import ru.nxckywhxte.repository.AdminRepository;
import ru.nxckywhxte.repository.TokenRepository;

import java.io.IOException;


@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AdminRepository adminRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        var admin = AdminEntity.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(request.getRoles())
                .build();
        var savedAdmin = adminRepository.save(admin);
        var jwtToken = jwtService.generateToken(admin);
        var refreshToken = jwtService.generateRefreshToken(admin);
        saveAdminToken(savedAdmin, jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var admin = adminRepository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(admin);
        var refreshToken = jwtService.generateRefreshToken(admin);
        revokeAllAdminTokens(admin);
        saveAdminToken(admin, jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    private void saveAdminToken(AdminEntity admin, String jwtToken) {
        var token = TokenEntity.builder()
                .admin(admin)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllAdminTokens(AdminEntity admin) {
        var validAdminTokens = tokenRepository.findAllValidTokenByUser(admin.getId());
        if (validAdminTokens.isEmpty())
            return;
        validAdminTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validAdminTokens);
    }

    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String adminEmail;
        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        adminEmail = jwtService.extractUsername(refreshToken);
        if (adminEmail != null) {
            var admin = this.adminRepository.findByEmail(adminEmail)
                    .orElseThrow();
            if (jwtService.isTokenValid(refreshToken, admin)) {
                var accessToken = jwtService.generateToken(admin);
                revokeAllAdminTokens(admin);
                saveAdminToken(admin, accessToken);
                var authResponse = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }
}
