package com.complefit.complefit.workout.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;

import java.util.List;

public record WorkoutUpdateRequestDTO(

        @Size(max = 100, message = "Title must be at most 100 characters long")
        String title,

        @Size(max = 500, message = "Description must be at most 500 characters long")
        String description,

        @Size(max = 500, message = "Notes must be at most 500 characters long")
        String notes,

        String visibility,

        List<@Valid WorkoutExerciseRequestDTO> exercises
) {}

