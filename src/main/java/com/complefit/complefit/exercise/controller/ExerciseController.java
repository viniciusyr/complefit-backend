package com.complefit.complefit.exercise.controller;

import com.complefit.complefit.exercise.dto.ExerciseRequestDTO;
import com.complefit.complefit.exercise.dto.ExerciseResponseDTO;
import com.complefit.complefit.exercise.dto.ExerciseUpdateDTO;
import com.complefit.complefit.exercise.service.ExerciseService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/exercises")
public class ExerciseController {

    private final ExerciseService exerciseService;

    public ExerciseController(ExerciseService exerciseService) {
        this.exerciseService = exerciseService;
    }

    @PostMapping
    public ResponseEntity<ExerciseResponseDTO> create(@Valid @RequestBody ExerciseRequestDTO dto) {
        return ResponseEntity.ok(exerciseService.createExercise(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExerciseResponseDTO> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(exerciseService.getExerciseById(id));
    }

    @GetMapping
    public ResponseEntity<List<ExerciseResponseDTO>> getAll() {
        return ResponseEntity.ok(exerciseService.getAllExercises());
    }

    @GetMapping("/search")
    public ResponseEntity<List<ExerciseResponseDTO>> search(@RequestParam String name) {
        return ResponseEntity.ok(exerciseService.searchByName(name));
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<ExerciseResponseDTO>> getByCategory(@PathVariable String category) {
        return ResponseEntity.ok(exerciseService.getByCategory(category));
    }

    @GetMapping("/muscle/{muscleGroup}")
    public ResponseEntity<List<ExerciseResponseDTO>> getByMuscleGroup(@PathVariable String muscleGroup) {
        return ResponseEntity.ok(exerciseService.getByMuscleGroup(muscleGroup));
    }

    @GetMapping("/equipment/{equipment}")
    public ResponseEntity<List<ExerciseResponseDTO>> getByEquipment(@PathVariable String equipment) {
        return ResponseEntity.ok(exerciseService.getByEquipment(equipment));
    }

    @GetMapping("/difficulty/{difficulty}")
    public ResponseEntity<List<ExerciseResponseDTO>> getByDifficulty(@PathVariable String difficulty) {
        return ResponseEntity.ok(exerciseService.getByDifficulty(difficulty));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExerciseResponseDTO> update(
            @PathVariable UUID id,
            @Valid @RequestBody ExerciseUpdateDTO dto
    ) {
        return ResponseEntity.ok(exerciseService.updateExercise(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        exerciseService.deleteExercise(id);
        return ResponseEntity.noContent().build();
    }
}
