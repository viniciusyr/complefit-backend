package com.complefit.CompleFit.trainer.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public record TrainerUpdateDTO(

        @Pattern(regexp = "^[0-9]{6,10}$", message = "CREF must be numeric and valid")
        String cref,

        String specialty,

        @PositiveOrZero(message = "Years of experience must be >= 0")
        Integer yearsOfExperience,

        @Size(max = 500, message = "Bio cannot exceed 500 characters")
        String bio

) {}
