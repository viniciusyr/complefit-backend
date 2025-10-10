package com.complefit.complefit.workoutexercise.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record WorkoutExerciseUpdateRequestDTO(

        @NotBlank
        String exerciseName,

        String exerciseId,

        String description,

        String videoUrl,

        @Min(1)
        Integer sets,

        @Min(1)
        Integer repetitions,

        @Min(0)
        Double weight,

        @Min(0)
        Integer restTimeSeconds,

        @Min(0)
        Integer durationSeconds
) {}
