package com.complefit.complefit.workout.controller;

import com.complefit.complefit.workout.dto.WorkoutRequestDTO;
import com.complefit.complefit.workout.dto.WorkoutResponseDTO;
import com.complefit.complefit.workout.dto.WorkoutUpdateRequestDTO;
import com.complefit.complefit.workout.service.WorkoutService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/api/workouts")
@RequiredArgsConstructor
public class WorkoutController {

    private final WorkoutService workoutService;

    @PostMapping
    public ResponseEntity<WorkoutResponseDTO> create(@Valid @RequestBody WorkoutRequestDTO request) {
        WorkoutResponseDTO response = workoutService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkoutResponseDTO> getById(@PathVariable UUID id) {
        WorkoutResponseDTO response = workoutService.getById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/trainer/{trainerId}")
    public ResponseEntity<List<WorkoutResponseDTO>> getByTrainer(@PathVariable UUID trainerId) {
        List<WorkoutResponseDTO> response = workoutService.getByTrainer(trainerId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<WorkoutResponseDTO>> getByStudent(@PathVariable UUID studentId) {
        List<WorkoutResponseDTO> response = workoutService.getByStudent(studentId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<WorkoutResponseDTO> update(@PathVariable UUID id, @Valid @RequestBody WorkoutUpdateRequestDTO request) {
        WorkoutResponseDTO response = workoutService.update(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        workoutService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
