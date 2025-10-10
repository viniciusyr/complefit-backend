package com.complefit.complefit.workoutexercise.controller;

import com.complefit.complefit.workoutexercise.dto.WorkoutExerciseRequestDTO;
import com.complefit.complefit.workoutexercise.dto.WorkoutExerciseResponseDTO;
import com.complefit.complefit.workoutexercise.dto.WorkoutExerciseUpdateRequestDTO;
import com.complefit.complefit.workoutexercise.service.WorkoutExerciseService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/workout-exercises")
public class WorkoutExerciseController {

    private final WorkoutExerciseService workoutExerciseService;

    public WorkoutExerciseController(WorkoutExerciseService service) {
        this.workoutExerciseService = service;
    }

    @PostMapping
    public ResponseEntity<WorkoutExerciseResponseDTO> create(
            @Valid @RequestBody WorkoutExerciseRequestDTO dto) {
        WorkoutExerciseResponseDTO created = workoutExerciseService.create(dto);
        return ResponseEntity.created(URI.create("/api/workout-exercises/" + created.id()))
                .body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkoutExerciseResponseDTO> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(workoutExerciseService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<WorkoutExerciseResponseDTO>> findAll() {
        return ResponseEntity.ok(workoutExerciseService.findAll());
    }


    @PutMapping("/{id}")
    public ResponseEntity<WorkoutExerciseResponseDTO> update(
            @PathVariable UUID id,
            @Valid @RequestBody WorkoutExerciseUpdateRequestDTO dto) {

        return ResponseEntity.ok(workoutExerciseService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        workoutExerciseService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
