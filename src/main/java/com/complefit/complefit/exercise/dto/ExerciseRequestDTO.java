package com.complefit.complefit.exercise.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ExerciseRequestDTO(
        @NotBlank(message = "Exercise name is required")
        @Size(max = 150)
        String name,
        String description,
        String category,
        String muscleGroup,
        String equipment,
        String difficulty,
        String videoUrl,
        String imageUrl
) {}
