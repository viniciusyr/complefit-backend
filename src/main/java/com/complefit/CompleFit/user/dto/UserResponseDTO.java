package com.complefit.CompleFit.user.dto;

import com.complefit.CompleFit.user.domain.Gender;
import com.complefit.CompleFit.user.domain.UserRole;
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
