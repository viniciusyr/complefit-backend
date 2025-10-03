package com.complefit.CompleFit.trainer.dto;

import jakarta.validation.constraints.*;

import java.util.UUID;

public record TrainerRequestDTO(

        @NotNull(message = "User ID is required")
        UUID userId,

        @NotBlank(message = "CREF is required")
        @Pattern(regexp = "^[0-9]{6,10}$", message = "CREF must be numeric and valid")
        String cref,

        @NotBlank(message = "Specialty is required")
        String specialty,

        @PositiveOrZero(message = "Years of experience must be >= 0")
        Integer yearsOfExperience,

        @Size(max = 500, message = "Bio cannot exceed 500 characters")
        String bio

) {}
