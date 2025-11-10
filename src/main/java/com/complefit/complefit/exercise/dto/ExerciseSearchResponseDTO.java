package com.complefit.complefit.exercise.dto;

import java.util.List;

public record ExerciseSearchResponseDTO(
        List<ExerciseDTO> exercises,
        int total,
        int limit,
        int offset
) {}
