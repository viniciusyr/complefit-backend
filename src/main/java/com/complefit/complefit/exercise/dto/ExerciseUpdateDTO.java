package com.complefit.complefit.exercise.dto;

public record ExerciseUpdateDTO(
        String name,
        String description,
        String category,
        String muscleGroup,
        String equipment,
        String difficulty,
        String videoUrl,
        String imageUrl
) {}
