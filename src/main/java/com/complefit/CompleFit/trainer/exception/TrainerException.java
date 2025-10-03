package com.complefit.CompleFit.trainer.exception;

import com.complefit.CompleFit.infra.exceptions.GlobalException;
import org.springframework.http.HttpStatus;

public class TrainerException extends GlobalException {
    public TrainerException(String message, HttpStatus status) {
        super(message, status);
    }

    public static TrainerException notFound(String id) {
        return new TrainerException("Trainer with id " + id + " not found", HttpStatus.NOT_FOUND);
    }

    public static TrainerException crefAlreadyExists(String cref) {
        return new TrainerException("CREF already registered: " + cref, HttpStatus.CONFLICT);
    }
}
