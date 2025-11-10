package com.complefit.complefit.exercise.exception;

import com.complefit.complefit.infra.exceptions.GlobalException;
import org.springframework.http.HttpStatus;
import java.util.UUID;

public class ExerciseException extends GlobalException {
    public ExerciseException(String message, HttpStatus status) {
        super(message, status);
    }

    public static ExerciseException notFound(UUID id) {
        return new ExerciseException("Exercise with id " + id + " not found", HttpStatus.NOT_FOUND);
    }
}
