package com.complefit.complefit.workout.dto;

import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.util.List;

public record WorkoutUpdateDTO(

        @Size(max = 100, message = "Title must be at most 100 characters long")
        String title,

        @Size(max = 500, message = "Description must be at most 500 characters long")
        String description,

        List<WorkoutExerciseRequestDTO> exercises,

        @PositiveOrZero(message = "Total duration must be zero or positive")
        Integer totalDurationSeconds
) {}
