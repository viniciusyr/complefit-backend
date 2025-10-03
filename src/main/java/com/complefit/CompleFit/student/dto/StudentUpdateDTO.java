package com.complefit.CompleFit.student.dto;

import jakarta.validation.constraints.Size;

public record StudentUpdateDTO(

        @Size(max = 255, message = "Goal cannot exceed 255 characters")
        String goal,

        String level,

        @Size(max = 500, message = "Medical restrictions cannot exceed 500 characters")
        String medicalRestrictions

) {}
