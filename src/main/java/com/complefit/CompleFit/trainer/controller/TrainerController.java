package com.complefit.CompleFit.trainer.controller;

import com.complefit.CompleFit.trainer.dto.TrainerRequestDTO;
import com.complefit.CompleFit.trainer.dto.TrainerResponseDTO;
import com.complefit.CompleFit.trainer.dto.TrainerUpdateDTO;
import com.complefit.CompleFit.trainer.service.TrainerService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/trainers")
public class TrainerController {

    private final TrainerService trainerService;

    public TrainerController(TrainerService trainerService) {
        this.trainerService = trainerService;
    }

    @PostMapping
    public ResponseEntity<TrainerResponseDTO> create(@Valid @RequestBody TrainerRequestDTO dto) {
        return ResponseEntity.ok(trainerService.createTrainer(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TrainerResponseDTO> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(trainerService.getTrainerById(id));
    }

    @GetMapping
    public ResponseEntity<List<TrainerResponseDTO>> getAll() {
        return ResponseEntity.ok(trainerService.getAllTrainers());
    }

    @PutMapping("/{id}")
    public ResponseEntity<TrainerResponseDTO> update(
            @PathVariable UUID id,
            @Valid @RequestBody TrainerUpdateDTO dto
    ) {
        return ResponseEntity.ok(trainerService.updateTrainer(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        trainerService.deleteTrainer(id);
        return ResponseEntity.noContent().build();
    }
}
