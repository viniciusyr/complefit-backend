package com.complefit.complefit.workout.mapper;

import com.complefit.complefit.user.domain.User;
import com.complefit.complefit.workout.domain.Workout;
import com.complefit.complefit.workout.domain.WorkoutVisibility;
import com.complefit.complefit.workout.dto.WorkoutRequestDTO;
import com.complefit.complefit.workout.dto.WorkoutResponseDTO;
import com.complefit.complefit.workout.dto.WorkoutUpdateRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class WorkoutMapper {

    private final WorkoutExerciseMapper exerciseMapper;

    @Autowired
    public WorkoutMapper(WorkoutExerciseMapper exerciseMapper) {
        this.exerciseMapper = exerciseMapper;
    }

    public Workout toEntity(WorkoutRequestDTO request, User trainer, User student) {
        List<WorkoutExercise> exercises = request.exercises().stream()
                .map(exerciseMapper::toEntity)
                .toList();

        return new Workout(
                request.title(),
                request.description(),
                trainer,
                student,
                exercises,
                request.notes(),
                request.visibility()
        );
    }

    public void updateEntity(Workout workout, WorkoutUpdateRequestDTO request) {
        if (request.title() != null) workout.setTitle(request.title());
        if (request.description() != null) workout.setDescription(request.description());
        if (request.notes() != null) workout.setNotes(request.notes());
        if (request.visibility() != null)
            workout.setVisibility(WorkoutVisibility.valueOf(request.visibility()));

        if (request.exercises() != null && !request.exercises().isEmpty()) {
            List<WorkoutExercise> updatedExercises = request.exercises().stream()
                    .map(exerciseMapper::toEntity)
                    .toList();

            workout.getExercises().clear();
            workout.getExercises().addAll(updatedExercises);
        }
    }

    public WorkoutResponseDTO toResponse(Workout workout) {
        return new WorkoutResponseDTO(
                workout.getId(),
                workout.getTitle(),
                workout.getDescription(),
                workout.getNotes(),
                workout.getVisibility().name(),
                workout.getTrainer() != null ? workout.getTrainer().getId() : null,
                workout.getStudent() != null ? workout.getStudent().getId() : null,
                workout.getExercises().stream()
                        .map(exerciseMapper::toResponse)
                        .toList(),
                workout.getTotalDuration(),
                workout.getCreatedAt(),
                workout.getUpdatedAt()
        );
    }


    public List<WorkoutResponseDTO> toResponseList(List<Workout> workouts) {
        return workouts.stream()
                .map(this::toResponse)
                .toList();
    }
}

