package com.complefit.CompleFit.auth.repository;

import com.complefit.CompleFit.auth.domain.AuthToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AuthTokenRepository extends JpaRepository<AuthToken, UUID> {
    Optional<AuthToken> findByRefreshToken(String refreshToken);
    void deleteByRefreshToken(String refreshToken);
}
