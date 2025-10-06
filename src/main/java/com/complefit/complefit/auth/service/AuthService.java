package com.complefit.complefit.auth.service;

import com.complefit.complefit.auth.domain.AuthToken;
import com.complefit.complefit.auth.dto.AuthResponseDTO;
import com.complefit.complefit.auth.exception.AuthException;
import com.complefit.complefit.auth.repository.AuthTokenRepository;
import com.complefit.complefit.user.domain.User;
import com.complefit.complefit.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class AuthService {

    @Autowired
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
