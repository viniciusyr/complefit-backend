package com.complefit.CompleFit.auth.exception;

import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.complefit.CompleFit.infra.exceptions.GlobalException;
import org.springframework.http.HttpStatus;

public class AuthException extends GlobalException {
    public AuthException(String message, HttpStatus status) {
        super(message, status);
    }

        public static AuthException tokenGenerationException(JWTCreationException e) {
            return new AuthException("Error while generating token: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        public static AuthException invalidTokenException(String token) {
            return new AuthException("Invalid or malformed token: " + token, HttpStatus.UNAUTHORIZED);
        }

        public static AuthException expiredTokenException() {
            return new AuthException("Token has expired. Please refresh your session.", HttpStatus.UNAUTHORIZED);
        }

        public static AuthException tokenVerificationException(JWTVerificationException e) {
            return new AuthException("Failed to verify token: " + e.getMessage(), HttpStatus.UNAUTHORIZED);
        }

        public static AuthException invalidCredentials() {
            return new AuthException("Invalid email or password.", HttpStatus.UNAUTHORIZED);
        }

        public static AuthException userNotFound(String email) {
            return new AuthException("User not found with email: " + email, HttpStatus.NOT_FOUND);
        }

        public static AuthException accountDisabled() {
            return new AuthException("Account is disabled. Please contact support.", HttpStatus.FORBIDDEN);
        }

        public static AuthException refreshTokenExpired() {
            return new AuthException("Refresh token has expired. Please log in again.", HttpStatus.UNAUTHORIZED);
        }

        public static AuthException invalidRefreshToken() {
            return new AuthException("Invalid refresh token provided.", HttpStatus.BAD_REQUEST);
        }

        public static AuthException accessDenied() {
            return new AuthException("Access denied. You don't have permission for this resource.", HttpStatus.FORBIDDEN);
        }

}
