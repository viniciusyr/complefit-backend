package com.complefit.complefit.trainer.dto;

import java.util.UUID;

public record TrainerResponseDTO(
        UUID id,
        UUID userId,
        String fullName,
        String cref,
        String specialty,
        Integer yearsOfExperience,
        String bio
) {}