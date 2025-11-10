package com.complefit.complefit.exercise.dto;

import java.util.UUID;

public record ExerciseResponseDTO(
        UUID id,
        String name,
        String description,
        String category,
        String muscleGroup,
        String equipment,
        String difficulty,
        String videoUrl,
        String imageUrl
) {}
