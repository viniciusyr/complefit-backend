package com.complefit.complefit.workoutexercise.mapper;

import com.complefit.complefit.workoutexercise.domain.WorkoutExercise;
import com.complefit.complefit.workoutexercise.dto.WorkoutExerciseRequestDTO;
import com.complefit.complefit.workoutexercise.dto.WorkoutExerciseResponseDTO;
import com.complefit.complefit.workoutexercise.dto.WorkoutExerciseUpdateRequestDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class WorkoutExerciseMapper {

    public WorkoutExercise toEntity(WorkoutExerciseRequestDTO dto) {
        if (dto == null) return null;

        return WorkoutExercise.builder()
                .exerciseName(dto.exerciseName())
                .description(dto.description())
                .videoUrl(dto.videoUrl())
                .sets(dto.sets())
                .repetitions(dto.repetitions())
                .weight(dto.weight())
                .restTimeSeconds(dto.restTimeSeconds())
                .durationSeconds(dto.durationSeconds())
                .build();
    }

    public WorkoutExerciseResponseDTO toResponse(WorkoutExercise entity) {
        if (entity == null) return null;

        return new WorkoutExerciseResponseDTO(
                entity.getId(),
                entity.getExerciseId(),
                entity.getExerciseName(),
                entity.getDescription(),
                entity.getVideoUrl(),
                entity.getSets(),
                entity.getRepetitions(),
                entity.getWeight(),
                entity.getRestTimeSeconds(),
                entity.getDurationSeconds(),
                entity.getTotalDuration()
        );
    }

    public List<WorkoutExercise> toEntityList(List<WorkoutExerciseRequestDTO> dtoList) {
        if (dtoList == null) return List.of();
        return dtoList.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }

    public List<WorkoutExerciseResponseDTO> toResponseList(List<WorkoutExercise> entities) {
        if (entities == null) return List.of();
        return entities.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public void updateEntity(WorkoutExercise entity, WorkoutExerciseUpdateRequestDTO dto) {
        if (dto.exerciseName() != null) entity.setExerciseName(dto.exerciseName());
        if (dto.description() != null) entity.setDescription(dto.description());
        if (dto.videoUrl() != null) entity.setVideoUrl(dto.videoUrl());
        if (dto.sets() != null) entity.setSets(dto.sets());
        if (dto.repetitions() != null) entity.setRepetitions(dto.repetitions());
        if (dto.weight() != null) entity.setWeight(dto.weight());
        if (dto.restTimeSeconds() != null) entity.setRestTimeSeconds(dto.restTimeSeconds());
        if (dto.durationSeconds() != null) entity.setDurationSeconds(dto.durationSeconds());
    }
}
