package com.complefit.CompleFit.user.dto;


import com.complefit.CompleFit.user.domain.Gender;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

public record UserUpdateDTO(
        String firstName,

        String lastName,

        @Pattern(regexp = "^\\+?[0-9]{8,15}$", message = "Phone number must be valid")
        String phoneNumber,

        @Past(message = "Birth date must be in the past")
        LocalDate birthDate,

        Gender gender,

        @Positive(message = "Height must be positive")
        Double height,

        @Positive(message = "Weight must be positive")
        Double weight
) {}
