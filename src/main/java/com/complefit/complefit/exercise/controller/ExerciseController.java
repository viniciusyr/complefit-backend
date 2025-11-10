package com.complefit.complefit.exercise.controller;

import com.complefit.complefit.exercise.dto.ExerciseDTO;
import com.complefit.complefit.exercise.service.ExerciseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exercises")
@Tag(name = "Exercises", description = "Exercise database operations from ExerciseDB API")
public class ExerciseController {

    private final ExerciseService exerciseService;

    public ExerciseController(ExerciseService exerciseService) {
        this.exerciseService = exerciseService;
    }

    @GetMapping
    @Operation(summary = "Get all exercises with pagination")
    public ResponseEntity<List<ExerciseDTO>> getAll(
            @Parameter(description = "Maximum number of results") @RequestParam(defaultValue = "20") int limit,
            @Parameter(description = "Number of results to skip") @RequestParam(defaultValue = "0") int offset
    ) {
        return ResponseEntity.ok(exerciseService.getAll(limit, offset));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get exercise by ID")
    public ResponseEntity<ExerciseDTO> getById(@PathVariable String id) {
        return ResponseEntity.ok(exerciseService.getById(id));
    }

    @GetMapping("/search/name/{name}")
    @Operation(summary = "Search exercises by name")
    public ResponseEntity<List<ExerciseDTO>> searchByName(
            @PathVariable String name,
            @RequestParam(defaultValue = "20") int limit,
            @RequestParam(defaultValue = "0") int offset
    ) {
        return ResponseEntity.ok(exerciseService.searchByName(name, limit, offset));
    }

    @GetMapping("/search/target/{target}")
    @Operation(summary = "Search exercises by target muscle", description = "Example: glutes, biceps, triceps")
    public ResponseEntity<List<ExerciseDTO>> searchByTarget(
            @PathVariable String target,
            @RequestParam(defaultValue = "20") int limit,
            @RequestParam(defaultValue = "0") int offset
    ) {
        return ResponseEntity.ok(exerciseService.searchByTarget(target, limit, offset));
    }

    @GetMapping("/search/bodypart/{bodyPart}")
    @Operation(summary = "Search exercises by body part", description = "Example: back, chest, legs")
    public ResponseEntity<List<ExerciseDTO>> searchByBodyPart(
            @PathVariable String bodyPart,
            @RequestParam(defaultValue = "20") int limit,
            @RequestParam(defaultValue = "0") int offset
    ) {
        return ResponseEntity.ok(exerciseService.searchByBodyPart(bodyPart, limit, offset));
    }

    @GetMapping("/search/equipment/{equipment}")
    @Operation(summary = "Search exercises by equipment", description = "Example: dumbbell, barbell, cable, bodyweight")
    public ResponseEntity<List<ExerciseDTO>> searchByEquipment(
            @PathVariable String equipment,
            @RequestParam(defaultValue = "20") int limit,
            @RequestParam(defaultValue = "0") int offset
    ) {
        return ResponseEntity.ok(exerciseService.searchByEquipment(equipment, limit, offset));
    }
}
