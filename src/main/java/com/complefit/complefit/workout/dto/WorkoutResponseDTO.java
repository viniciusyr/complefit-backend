package com.complefit.complefit.workout.dto;

import java.time.Instant;
import java.util.UUID;

public record WorkoutResponseDTO(

        UUID id,
        String title,
        String description,
        Instant dateCreated,

        UUID trainerId,
        String trainerName,

        UUID studentId,
        String studentName,

        Integer totalDurationSeconds,
        String visibility,

        List<WorkoutExerciseResponseDTO> exercises
) {}
