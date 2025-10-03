package com.complefit.CompleFit.trainer.service;

import com.complefit.CompleFit.trainer.domain.Trainer;
import com.complefit.CompleFit.trainer.dto.TrainerRequestDTO;
import com.complefit.CompleFit.trainer.dto.TrainerResponseDTO;
import com.complefit.CompleFit.trainer.dto.TrainerUpdateDTO;
import com.complefit.CompleFit.trainer.exception.TrainerException;
import com.complefit.CompleFit.trainer.mapper.TrainerMapper;
import com.complefit.CompleFit.trainer.repository.TrainerRepository;
import com.complefit.CompleFit.user.domain.User;
import com.complefit.CompleFit.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TrainerService {

    private final TrainerRepository trainerRepository;
    private final UserRepository userRepository;

    public TrainerService(TrainerRepository trainerRepository, UserRepository userRepository) {
        this.trainerRepository = trainerRepository;
        this.userRepository = userRepository;
    }

    public TrainerResponseDTO createTrainer(TrainerRequestDTO dto) {
        User user = userRepository.findById(dto.userId())
                .orElseThrow(() -> TrainerException.notFound(dto.userId()));

        if (trainerRepository.existsByCref(dto.cref())) {
            throw TrainerException.crefAlreadyExists(dto.cref());
        }

        Trainer trainer = TrainerMapper.toEntity(dto, user);
        return TrainerMapper.toResponse(trainerRepository.save(trainer));
    }

    public TrainerResponseDTO getTrainerById(UUID id) {
        Trainer trainer = trainerRepository.findById(id)
                .orElseThrow(() -> TrainerException.notFound(id));
        return TrainerMapper.toResponse(trainer);
    }

    public List<TrainerResponseDTO> getAllTrainers() {
        return trainerRepository.findAll().stream()
                .map(TrainerMapper::toResponse)
                .collect(Collectors.toList());
    }

    public TrainerResponseDTO updateTrainer(UUID id, TrainerUpdateDTO dto) {
        Trainer trainer = trainerRepository.findById(id)
                .orElseThrow(() -> TrainerException.notFound(id));

        TrainerMapper.updateEntity(trainer, dto);
        return TrainerMapper.toResponse(trainerRepository.save(trainer));
    }

    public void deleteTrainer(UUID id) {
        if (!trainerRepository.existsById(id)) {
            throw TrainerException.notFound(id);
        }
        trainerRepository.deleteById(id);
    }
}
