package com.complefit.complefit.user.dto;

import com.complefit.complefit.user.domain.UserRole;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public record UserResponseDTO(

        @NotNull UUID id,
        String firstName,
        String lastName,
        String email,
        String phoneNumber,
        LocalDate birthDate,
        String gender,
        Double height,
        Double weight,
        UserRole role

) {}
