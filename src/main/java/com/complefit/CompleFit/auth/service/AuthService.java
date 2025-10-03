package com.complefit.CompleFit.auth.service;

import com.complefit.CompleFit.auth.domain.AuthToken;
import com.complefit.CompleFit.auth.repository.AuthTokenRepository;
import com.complefit.CompleFit.user.domain.User;
import com.complefit.CompleFit.user.repository.UserRepository;
import lombok.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class AuthService {

    private final AuthTokenRepository authTokenRepository;
    private final UserRepository userRepository;

    @Value("${app.jwt.secret}")
    private String jwtSecret;

    @Value("${app.jwt.expiration}")
    private Long jwtExpiration;

    @Value("${app.jwt.refresh-expiration}")
    private Long refreshExpiration;

    public AuthService(AuthTokenRepository authTokenRepository, UserRepository userRepository) {
        this.authTokenRepository = authTokenRepository;
        this.userRepository = userRepository;
    }

    private String generateAccessToken(User user) {
        return Jwts.builder()
                .setSubject(user.getId().toString())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(SignatureAlgorithm.HS256, jwtSecret)
                .compact();
    }

    // Login → gera tokens
    public AuthResponse login(String email, String password) {
        Optional<User> userOpt = userRepository.findByEmail(email);

        if (userOpt.isEmpty()) {
            throw new RuntimeException("Invalid credentials");
        }

        User user = userOpt.get();

        if (!password.equals(user.getPasswordHash())) {
            throw new RuntimeException("Invalid credentials");
        }

        String accessToken = generateAccessToken(user);

        String refreshToken = UUID.randomUUID().toString();
        AuthToken authToken = new AuthToken();
        authToken.setRefreshToken(refreshToken);
        authToken.setUserId(user.getId());
        authToken.setExpiryDate(LocalDateTime.now().plusMinutes(refreshExpiration));
        authTokenRepository.save(authToken);

        return new AuthResponse(accessToken, refreshToken);
    }

    // Refresh Token
    public AuthResponse refresh(String refreshToken) {
        AuthToken token = authTokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new RuntimeException("Invalid refresh token"));

        if (token.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Refresh token expired");
        }

        User user = userRepository.findById(token.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String newAccessToken = generateAccessToken(user);

        return new AuthResponse(newAccessToken, refreshToken);
    }

    public void logout(String refreshToken) {
        authTokenRepository.deleteByRefreshToken(refreshToken);
    }
}
