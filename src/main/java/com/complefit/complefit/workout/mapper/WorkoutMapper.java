package com.complefit.complefit.workout.mapper;

import com.complefit.complefit.user.domain.User;
import com.complefit.complefit.workout.domain.Workout;
import com.complefit.complefit.workout.domain.WorkoutVisibility;
import com.complefit.complefit.workout.dto.WorkoutRequestDTO;
import com.complefit.complefit.workout.dto.WorkoutResponseDTO;
import com.complefit.complefit.workout.dto.WorkoutUpdateDTO;
import com.complefit.complefit.workoutexercise.domain.WorkoutExercise;
import com.complefit.complefit.workoutexercise.mapper.WorkoutExerciseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class WorkoutMapper {

    private final WorkoutExerciseMapper exerciseMapper;

    @Autowired
    public WorkoutMapper(WorkoutExerciseMapper exerciseMapper) {
        this.exerciseMapper = exerciseMapper;
    }

    public Workout toEntity(WorkoutRequestDTO request, User trainer, User student) {
        List<WorkoutExercise> exercises = exerciseMapper.toEntityList(request.exercises());

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

    public void updateEntity(Workout workout, WorkoutUpdateDTO request) {
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
                workout.getTrainer() != null ? workout.getTrainer().getId() : null,
                workout.getTrainer() != null ? workout.getTrainer().getFirstName() + " " + workout.getTrainer().getLastName()  : null,
                workout.getStudent().getId(),
                workout.getStudent().getFirstName() + " " + workout.getStudent().getLastName(),
                workout.getVisibility().name(),
                workout.getExercises().stream()
                        .map(exerciseMapper::toResponse)
                        .toList(),
                workout.getTotalDuration(),
                workout.getNotes(),
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

