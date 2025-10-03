package com.complefit.CompleFit.student.exception;

import com.complefit.CompleFit.infra.exceptions.GlobalException;
import org.springframework.http.HttpStatus;

import java.util.UUID;

public class StudentException extends GlobalException {
    public StudentException(String message, HttpStatus status) {
        super(message, status);
    }

    public static StudentException notFound(UUID id) {
        return new StudentException("Student with id " + id + " not found", HttpStatus.NOT_FOUND);
    }
}
