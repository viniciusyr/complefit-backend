package com.complefit.complefit.workout.exception;

import com.complefit.complefit.infra.exceptions.GlobalException;
import org.springframework.http.HttpStatus;

import java.util.UUID;

public class WorkoutException extends GlobalException {

    public WorkoutException(String message, HttpStatus status) {
        super(message, status);
    }

    public static WorkoutException notFound(UUID id){
        return new WorkoutException("Workout with id" + id + " not found", HttpStatus.BAD_REQUEST);
    }
}
