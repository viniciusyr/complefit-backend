package com.complefit.CompleFit.user.dto;

import com.complefit.CompleFit.user.domain.Gender;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record UserRequestDTO(
        @NotBlank(message = "First name is required")
        String firstName,

        @NotBlank(message = "Last name is required")
        String lastName,

        @Email(message = "Email should be valid")
        @NotBlank(message = "Email is required")
        String email,

        @NotBlank(message = "Password is required")
        @Size(min = 6, message = "Password must be at least 6 characters long")
        String password,

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
