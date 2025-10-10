package com.complefit.complefit.workoutexercise.dto;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record WorkoutExerciseRequestDTO(

        @NotBlank
        String exerciseName,

        String description,

        String videoUrl,

        @NotNull @Min(1)
        Integer sets,

        @NotNull @Min(1)
        Integer repetitions,

        @Min(1)
        Double weight,

        @Min(1)
        Integer restTimeSeconds,

        @Min(1)
        Integer durationSeconds
) {}
