package com.complefit.CompleFit.user.dto;



import jakarta.validation.constraints.Past;

import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record UserUpdateDTO(
        @Size(min = 2, max = 50)
        String firstName,

        @Size(min = 2, max = 50)
        String lastName,

        String phoneNumber,

        Double height,

        Double weight,

        String gender,

        @Past
        LocalDate birthDate
) {}
