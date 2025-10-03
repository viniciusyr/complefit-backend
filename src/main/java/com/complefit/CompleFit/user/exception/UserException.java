package com.complefit.CompleFit.user.exception;

import com.complefit.CompleFit.infra.exceptions.GlobalException;
import org.springframework.http.HttpStatus;

import java.util.UUID;

public class UserException extends GlobalException {
    public UserException(String message, HttpStatus status) {
        super(message, status);
    }

    public static UserException notFound(UUID id) {
        return new UserException("User with id " + id + " not found", HttpStatus.NOT_FOUND);
    }

    public static UserException emailNotFound(String email) {
        return new UserException("User with id " + email + " not found", HttpStatus.NOT_FOUND);
    }

    public static UserException emailAlreadyExists(String email) {
        return new UserException("Email already registered: " + email, HttpStatus.CONFLICT);
    }

    public static UserException invalidBirthDate() {
        return new UserException("Birth date cannot be in the future", HttpStatus.BAD_REQUEST);
    }
}
