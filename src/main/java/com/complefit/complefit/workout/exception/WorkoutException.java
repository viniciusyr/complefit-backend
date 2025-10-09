package com.complefit.complefit.workout.exception;

import com.complefit.complefit.infra.exceptions.GlobalException;
import org.springframework.http.HttpStatus;

public class WorkoutException extends GlobalException {

    protected WorkoutException(String message, HttpStatus status) {
        super(message, status);
    }

    public WorkoutException
}
