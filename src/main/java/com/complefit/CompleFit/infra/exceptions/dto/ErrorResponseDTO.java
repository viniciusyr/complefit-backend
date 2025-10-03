package com.complefit.CompleFit.infra.exceptions.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.http.HttpStatus;

import java.time.Instant;

public record ErrorResponseDTO(
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "UTC")
        Instant timestamp,
        int status,
        String error,
        String message,
        String path
) {
    public static ErrorResponseDTO of(HttpStatus status, String message, String path) {
        return new ErrorResponseDTO(
                Instant.now(),
                status.value(),
                status.getReasonPhrase(),
                message,
                path);
    }
}
