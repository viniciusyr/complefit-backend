package com.complefit.CompleFit.auth.service;

import com.complefit.CompleFit.auth.domain.AuthToken;
import com.complefit.CompleFit.auth.dto.AuthResponseDTO;
import com.complefit.CompleFit.auth.exception.AuthException;
import com.complefit.CompleFit.auth.repository.AuthTokenRepository;
import com.complefit.CompleFit.user.domain.User;
import com.complefit.CompleFit.user.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class AuthService {

    private final TokenService tokenService;
    private final UserRepository userRepository;
    private final AuthTokenRepository authTokenRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public AuthService(TokenService tokenService,
                       UserRepository userRepository,
                       AuthTokenRepository authTokenRepository) {
        this.tokenService = tokenService;
        this.userRepository = userRepository;
        this.authTokenRepository = authTokenRepository;
    }

    public AuthResponseDTO login(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(AuthException::invalidCredentials);

        if (!passwordEncoder.matches(password, user.getPasswordHash())) {
            throw AuthException.invalidCredentials();
        }

        if (!user.isEnabled()) {
            throw AuthException.accountDisabled();
        }

        String accessToken;
        try {
            accessToken = tokenService.generateToken(user);
        } catch (Exception e) {
            throw AuthException.tokenGenerationException(null);
        }

        String refreshToken = UUID.randomUUID().toString();

        AuthToken authToken = new AuthToken();
        authToken.setUserId(user.getId());
        authToken.setRefreshToken(refreshToken);
        authToken.setExpiryDate(LocalDateTime.now().plusDays(7));

        authTokenRepository.save(authToken);

        return new AuthResponseDTO(accessToken, refreshToken);
    }

    public AuthResponseDTO refresh(String refreshToken) {
        AuthToken token = authTokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(AuthException::invalidRefreshToken);

        if (token.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw AuthException.refreshTokenExpired();
        }

        User user = userRepository.findById(token.getUserId())
                .orElseThrow(() -> AuthException.userNotFound("unknown"));

        String newAccessToken;
        try {
            newAccessToken = tokenService.generateToken(user);
        } catch (Exception e) {
            throw AuthException.tokenGenerationException(null);
        }

        return new AuthResponseDTO(newAccessToken, refreshToken);
    }


    public void logout(String refreshToken) {
        boolean exists = authTokenRepository.existsByRefreshToken(refreshToken);
        if (!exists) {
            throw AuthException.invalidRefreshToken();
        }
        authTokenRepository.deleteByRefreshToken(refreshToken);
    }
}
