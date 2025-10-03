package com.complefit.CompleFit.infra.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class GlobalException extends RuntimeException {

    private final HttpStatus status;

    protected GlobalException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

}
