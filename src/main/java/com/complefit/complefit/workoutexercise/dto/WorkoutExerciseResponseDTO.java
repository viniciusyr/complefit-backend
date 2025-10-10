package com.complefit.complefit.workoutexercise.dto;

import java.util.UUID;

public record WorkoutExerciseResponseDTO(
        UUID id,
        String exerciseId,
        String exerciseName,
        String description,
        String videoUrl,
        Integer sets,
        Integer repetitions,
        Double weight,
        Integer restTimeSeconds,
        Integer durationSeconds,
        Integer totalDuration
) {}
