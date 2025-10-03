package com.complefit.CompleFit.student.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record StudentRequestDTO(

        @NotNull(message = "User ID is required")
        UUID userId,

        @NotBlank(message = "Goal is required")
        @Size(max = 255, message = "Goal cannot exceed 255 characters")
        String goal,

        @NotBlank(message = "Level is required")
        String level,

        @Size(max = 500, message = "Medical restrictions cannot exceed 500 characters")
        String medicalRestrictions

) {}
