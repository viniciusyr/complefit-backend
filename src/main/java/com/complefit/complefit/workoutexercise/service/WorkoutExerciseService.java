package com.complefit.complefit.workoutexercise.service;

import com.complefit.complefit.workoutexercise.dto.WorkoutExerciseRequestDTO;
import com.complefit.complefit.workoutexercise.dto.WorkoutExerciseResponseDTO;
import com.complefit.complefit.workoutexercise.dto.WorkoutExerciseUpdateRequestDTO;
import com.complefit.complefit.workoutexercise.mapper.WorkoutExerciseMapper;
import com.complefit.complefit.workoutexercise.repository.WorkoutExerciseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class WorkoutExerciseService {

    private final WorkoutExerciseRepository repository;
    private final WorkoutExerciseMapper mapper;

    public WorkoutExerciseService(WorkoutExerciseRepository repository, WorkoutExerciseMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Transactional
    public WorkoutExerciseResponseDTO create(WorkoutExerciseRequestDTO dto) {
        var entity = mapper.toEntity(dto);
        entity.getTotalDuration();
        var saved = repository.save(entity);
        return mapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public List<WorkoutExerciseResponseDTO> findAll() {
        return mapper.toResponseList(repository.findAll());
    }

    @Transactional(readOnly = true)
    public WorkoutExerciseResponseDTO findById(UUID id) {
        var entity = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Workout exercise not found"));
        return mapper.toResponse(entity);
    }

    @Transactional
    public WorkoutExerciseResponseDTO update(UUID id, WorkoutExerciseUpdateRequestDTO dto) {
        var entity = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Workout exercise not found"));

        mapper.updateEntity(entity, dto);
        entity.getTotalDuration();
        var updated = repository.save(entity);

        return mapper.toResponse(updated);
    }

    @Transactional
    public void delete(UUID id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("Workout exercise not found");
        }
        repository.deleteById(id);
    }
}
