package com.complefit.CompleFit.trainer.mapper;

import com.complefit.CompleFit.trainer.domain.Trainer;
import com.complefit.CompleFit.trainer.dto.TrainerRequestDTO;
import com.complefit.CompleFit.trainer.dto.TrainerResponseDTO;
import com.complefit.CompleFit.trainer.dto.TrainerUpdateDTO;
import com.complefit.CompleFit.user.domain.User;

public class TrainerMapper {

    public static Trainer toEntity(TrainerRequestDTO dto, User user) {
        Trainer trainer = new Trainer();
        trainer.setUser(user);
        trainer.setCref(dto.cref());
        trainer.setSpecialty(dto.specialty());
        trainer.setYearsOfExperience(dto.yearsOfExperience());
        trainer.setBio(dto.bio());
        return trainer;
    }

    public static void updateEntity(Trainer trainer, TrainerUpdateDTO dto) {
        if (dto.cref() != null) trainer.setCref(dto.cref());
        if (dto.specialty() != null) trainer.setSpecialty(dto.specialty());
        if (dto.yearsOfExperience() != null) trainer.setYearsOfExperience(dto.yearsOfExperience());
        if (dto.bio() != null) trainer.setBio(dto.bio());
    }

    public static TrainerResponseDTO toResponse(Trainer trainer) {
        return new TrainerResponseDTO(
                trainer.getId(),
                trainer.getUser().getId(),
                trainer.getUser().getFirstName() + " " + trainer.getUser().getLastName(),
                trainer.getCref(),
                trainer.getSpecialty(),
                trainer.getYearsOfExperience(),
                trainer.getBio()
        );
    }
}
