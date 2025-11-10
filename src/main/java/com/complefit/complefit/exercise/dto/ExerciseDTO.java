package com.complefit.complefit.exercise.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record ExerciseDTO(
        String id,
        String name,
        String gifUrl,
        List<String> instructions,
        @JsonProperty("target") String primaryMuscle,
        @JsonProperty("secondaryMuscles") List<String> secondaryMuscles,
        String bodyPart,
        String equipment
) {}
