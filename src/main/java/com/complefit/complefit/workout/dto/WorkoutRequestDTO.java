package com.complefit.complefit.workout.dto;

import com.complefit.complefit.workoutexercise.dto.WorkoutExerciseRequestDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.UUID;

public record WorkoutRequestDTO(

        @NotNull(message = "Student ID is required")
        UUID studentId,

        UUID trainerId,

        @NotBlank(message = "Title is required")
        @Size(max = 100, message = "Title must be at most 100 characters long")
        String title,

        @Size(max = 500, message = "Description must be at most 500 characters long")
        String description,

        @NotEmpty(message = "Workout must contain at least one exercise")
        List<WorkoutExerciseRequestDTO> exercises,

        @Size(max = 500, message = "Description must be at most 500 characters long")
        String notes,

        @NotEmpty
        String visibility
) {}
