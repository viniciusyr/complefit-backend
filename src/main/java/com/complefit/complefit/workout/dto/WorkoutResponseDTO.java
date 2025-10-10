package com.complefit.complefit.workout.dto;

import com.complefit.complefit.workoutexercise.dto.WorkoutExerciseResponseDTO;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record WorkoutResponseDTO(

        UUID id,
        String title,
        String description,

        UUID trainerId,
        String trainerName,

        UUID studentId,
        String studentName,

        String visibility,

        List<WorkoutExerciseResponseDTO> exercises,
        Integer totalDurationSeconds,

        String notes,

        Instant createdAt,
        Instant updatedAt
) {}
