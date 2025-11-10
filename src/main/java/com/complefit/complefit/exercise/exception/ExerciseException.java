package com.complefit.complefit.exercise.exception;

import com.complefit.complefit.infra.exceptions.GlobalException;
import org.springframework.http.HttpStatus;

public class ExerciseException extends GlobalException {
    public ExerciseException(String message, HttpStatus status) {
        super(message, status);
    }

    public static ExerciseException notFound(String id) {
        return new ExerciseException("Exercise with id " + id + " not found", HttpStatus.NOT_FOUND);
    }

    public static ExerciseException apiError(String message) {
        return new ExerciseException("ExerciseDB API error: " + message, HttpStatus.SERVICE_UNAVAILABLE);
    }

    public static ExerciseException invalidApiKey() {
        return new ExerciseException("Invalid or missing ExerciseDB API key. Please configure exercisedb.api-key in application properties", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
