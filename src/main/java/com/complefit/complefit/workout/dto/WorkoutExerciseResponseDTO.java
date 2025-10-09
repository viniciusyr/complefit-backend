package com.complefit.complefit.workout.dto;

import java.util.UUID;

public record WorkoutExerciseResponseDTO(

        UUID id,
        String externalExerciseId,
        int sets,
        int reps,
        double weight,
        int restTimeSeconds
) {}
