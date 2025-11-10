package com.complefit.complefit.exercise.mapper;

import com.complefit.complefit.exercise.domain.Exercise;
import com.complefit.complefit.exercise.dto.ExerciseRequestDTO;
import com.complefit.complefit.exercise.dto.ExerciseResponseDTO;
import com.complefit.complefit.exercise.dto.ExerciseUpdateDTO;

public class ExerciseMapper {

    public static Exercise toEntity(ExerciseRequestDTO dto) {
        Exercise exercise = new Exercise();
        exercise.setName(dto.name());
        exercise.setDescription(dto.description());
        exercise.setCategory(dto.category());
        exercise.setMuscleGroup(dto.muscleGroup());
        exercise.setEquipment(dto.equipment());
        exercise.setDifficulty(dto.difficulty());
        exercise.setVideoUrl(dto.videoUrl());
        exercise.setImageUrl(dto.imageUrl());
        return exercise;
    }

    public static void updateEntity(Exercise exercise, ExerciseUpdateDTO dto) {
        if (dto.name() != null) exercise.setName(dto.name());
        if (dto.description() != null) exercise.setDescription(dto.description());
        if (dto.category() != null) exercise.setCategory(dto.category());
        if (dto.muscleGroup() != null) exercise.setMuscleGroup(dto.muscleGroup());
        if (dto.equipment() != null) exercise.setEquipment(dto.equipment());
        if (dto.difficulty() != null) exercise.setDifficulty(dto.difficulty());
        if (dto.videoUrl() != null) exercise.setVideoUrl(dto.videoUrl());
        if (dto.imageUrl() != null) exercise.setImageUrl(dto.imageUrl());
    }

    public static ExerciseResponseDTO toResponse(Exercise exercise) {
        return new ExerciseResponseDTO(
                exercise.getId(),
                exercise.getName(),
                exercise.getDescription(),
                exercise.getCategory(),
                exercise.getMuscleGroup(),
                exercise.getEquipment(),
                exercise.getDifficulty(),
                exercise.getVideoUrl(),
                exercise.getImageUrl()
        );
    }
}
