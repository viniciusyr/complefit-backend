package com.complefit.CompleFit.student.dto;

import java.util.UUID;

public record StudentResponseDTO(
        UUID id,
        UUID userId,
        String fullName,
        String goal,
        String level,
        String medicalRestrictions
) {}
