package com.complefit.complefit.user.dto;



import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record UserUpdateDTO(
        @NotBlank
        @Size(min = 2, max = 50)
        String firstName,

        @NotBlank
        @Size(min = 2, max = 50)
        String lastName,

        @Pattern(regexp = "^\\+?[0-9]{8,15}$", message = "Phone number must be valid")
        String phoneNumber,

        @Positive(message = "Height must be positive")
        Double height,

        @Positive(message = "Weight must be positive")
        Double weight,

        @NotBlank
        String gender,

        @Past(message = "Birth date must be in the past")
        LocalDate birthDate,

        @Pattern(regexp = "\\d{11}", message = "CPF must contain at least 11 digits")
        String cpf
) {}
