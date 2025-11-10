package com.complefit.complefit.workoutexercise.dto;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record WorkoutExerciseRequestDTO(

        String exerciseId, // Optional: ID from ExerciseDB API

        @NotBlank
        String exerciseName,

        String description,

        String videoUrl,

        @NotNull @Min(1)
        Integer sets,

        @NotNull @Min(1)
        Integer repetitions,

        @Min(0)
        Double weight,

        @Min(0)
        Integer restTimeSeconds,

        @Min(0)
        Integer durationSeconds
) {}
