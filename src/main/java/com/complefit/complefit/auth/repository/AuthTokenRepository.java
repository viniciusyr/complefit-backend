package com.complefit.complefit.auth.repository;

import com.complefit.complefit.auth.domain.AuthToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AuthTokenRepository extends JpaRepository<AuthToken, UUID> {
    Optional<AuthToken> findByRefreshToken(String refreshToken);
    void deleteByRefreshToken(String refreshToken);
    boolean existsByRefreshToken(String refreshToken);
}
