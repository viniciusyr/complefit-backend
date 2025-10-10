package com.complefit.complefit.workout.service;

import com.complefit.complefit.student.exception.StudentException;
import com.complefit.complefit.trainer.exception.TrainerException;
import com.complefit.complefit.user.domain.User;
import com.complefit.complefit.user.repository.UserRepository;
import com.complefit.complefit.workout.domain.Workout;
import com.complefit.complefit.workout.domain.WorkoutVisibility;
import com.complefit.complefit.workout.dto.WorkoutRequestDTO;
import com.complefit.complefit.workout.dto.WorkoutResponseDTO;
import com.complefit.complefit.workout.dto.WorkoutUpdateDTO;
import com.complefit.complefit.workout.exception.WorkoutException;
import com.complefit.complefit.workout.mapper.WorkoutMapper;
import com.complefit.complefit.workout.repository.WorkoutRepository;
import com.complefit.complefit.workoutexercise.domain.WorkoutExercise;
import com.complefit.complefit.workoutexercise.mapper.WorkoutExerciseMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class WorkoutService {

    private final WorkoutRepository workoutRepository;
    private final UserRepository userRepository;
    private final WorkoutMapper workoutMapper;
    private final WorkoutExerciseMapper workoutExerciseMapper;

    public WorkoutService(
            WorkoutRepository workoutRepository,
            UserRepository userRepository,
            WorkoutMapper workoutMapper, WorkoutExerciseMapper workoutExerciseMapper
    ) {
        this.workoutRepository = workoutRepository;
        this.userRepository = userRepository;
        this.workoutMapper = workoutMapper;
        this.workoutExerciseMapper = workoutExerciseMapper;
    }

    @Transactional
    public WorkoutResponseDTO create(WorkoutRequestDTO request) {
        User trainer = null;

        if (request.trainerId() != null) {
            trainer = userRepository.findById(request.trainerId())
                    .orElseThrow(() -> TrainerException.notFound(request.trainerId()));
        }

        User student = userRepository.findById(request.studentId())
                .orElseThrow(() -> StudentException.notFound(request.studentId()));

        Workout workout = workoutMapper.toEntity(request, trainer, student);
        workoutRepository.save(workout);

        return workoutMapper.toResponse(workout);
    }

    @Transactional(readOnly = true)
    public WorkoutResponseDTO getById(UUID id) {
        Workout workout = workoutRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Workout not found"));
        return workoutMapper.toResponse(workout);
    }

    @Transactional(readOnly = true)
    public List<WorkoutResponseDTO> getByTrainer(UUID trainerId) {
        return workoutRepository.findByTrainerId(trainerId)
                .stream()
                .map(workoutMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<WorkoutResponseDTO> getByStudent(UUID studentId) {
        return workoutRepository.findByStudentId(studentId)
                .stream()
                .map(workoutMapper::toResponse)
                .toList();
    }

    @Transactional
    public WorkoutResponseDTO update(UUID id, WorkoutUpdateDTO request) {
        Workout workout = workoutRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Workout not found"));

        if (request.title() != null) workout.setTitle(request.title());
        if (request.description() != null) workout.setDescription(request.description());
        if (request.notes() != null) workout.setNotes(request.notes());
        if (request.visibility() != null)
            workout.setVisibility(WorkoutVisibility.valueOf(request.visibility()));

        if (request.exercises() != null && !request.exercises().isEmpty()) {
            List<WorkoutExercise> updatedExercises = workoutExerciseMapper.toEntityList(request.exercises());
            workout.getExercises().clear();
            workout.getExercises().addAll(updatedExercises);
        }

        workoutRepository.save(workout);

        return workoutMapper.toResponse(workout);
    }

    @Transactional
    public void delete(UUID id) {
        if (!workoutRepository.existsById(id)) {
            throw WorkoutException.notFound(id);
        }
        workoutRepository.deleteById(id);
    }
}

