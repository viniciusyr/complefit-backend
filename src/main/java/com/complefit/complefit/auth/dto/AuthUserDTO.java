package com.complefit.complefit.auth.dto;

import java.util.UUID;

public record AuthUserDTO(
        UUID id,
        String name,
        String email,
        String role
) {}
