package com.complefit.complefit.workout.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

public record WorkoutExerciseRequestDTO(

        @NotBlank(message = "Exercise ID is required")
        String externalExerciseId,

        @Min(value = 1, message = "Sets must be at least 1")
        int sets,

        @Min(value = 1, message = "Reps must be at least 1")
        int reps,

        @PositiveOrZero(message = "Weight must be zero or positive")
        double weight,

        @PositiveOrZero(message = "Rest time must be zero or positive")
        int restTimeSeconds
) {}
