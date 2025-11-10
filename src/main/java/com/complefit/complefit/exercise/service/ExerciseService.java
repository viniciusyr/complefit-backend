package com.complefit.complefit.exercise.service;

import com.complefit.complefit.exercise.domain.Exercise;
import com.complefit.complefit.exercise.dto.ExerciseRequestDTO;
import com.complefit.complefit.exercise.dto.ExerciseResponseDTO;
import com.complefit.complefit.exercise.dto.ExerciseUpdateDTO;
import com.complefit.complefit.exercise.exception.ExerciseException;
import com.complefit.complefit.exercise.mapper.ExerciseMapper;
import com.complefit.complefit.exercise.repository.ExerciseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ExerciseService {

    private final ExerciseRepository repository;

    public ExerciseService(ExerciseRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public ExerciseResponseDTO createExercise(ExerciseRequestDTO dto) {
        Exercise exercise = ExerciseMapper.toEntity(dto);
        return ExerciseMapper.toResponse(repository.save(exercise));
    }

    @Transactional(readOnly = true)
    public ExerciseResponseDTO getExerciseById(UUID id) {
        Exercise exercise = repository.findById(id)
                .orElseThrow(() -> ExerciseException.notFound(id));
        return ExerciseMapper.toResponse(exercise);
    }

    @Transactional(readOnly = true)
    public List<ExerciseResponseDTO> getAllExercises() {
        return repository.findAll().stream()
                .map(ExerciseMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ExerciseResponseDTO> searchByName(String name) {
        return repository.findByNameContainingIgnoreCase(name).stream()
                .map(ExerciseMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ExerciseResponseDTO> getByCategory(String category) {
        return repository.findByCategory(category).stream()
                .map(ExerciseMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ExerciseResponseDTO> getByMuscleGroup(String muscleGroup) {
        return repository.findByMuscleGroup(muscleGroup).stream()
                .map(ExerciseMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ExerciseResponseDTO> getByEquipment(String equipment) {
        return repository.findByEquipment(equipment).stream()
                .map(ExerciseMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ExerciseResponseDTO> getByDifficulty(String difficulty) {
        return repository.findByDifficulty(difficulty).stream()
                .map(ExerciseMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public ExerciseResponseDTO updateExercise(UUID id, ExerciseUpdateDTO dto) {
        Exercise exercise = repository.findById(id)
                .orElseThrow(() -> ExerciseException.notFound(id));
        ExerciseMapper.updateEntity(exercise, dto);
        return ExerciseMapper.toResponse(repository.save(exercise));
    }

    @Transactional
    public void deleteExercise(UUID id) {
        if (!repository.existsById(id)) {
            throw ExerciseException.notFound(id);
        }
        repository.deleteById(id);
    }
}
