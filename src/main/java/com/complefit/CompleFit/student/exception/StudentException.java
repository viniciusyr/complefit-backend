package com.complefit.CompleFit.student.exception;

import com.complefit.CompleFit.infra.exceptions.GlobalException;
import org.springframework.http.HttpStatus;

public class StudentException extends GlobalException {
    public StudentException(String message, HttpStatus status) {
        super(message, status);
    }

    public static StudentException notFound(String id) {
        return new StudentException("Student with id " + id + " not found", HttpStatus.NOT_FOUND);
    }
}
