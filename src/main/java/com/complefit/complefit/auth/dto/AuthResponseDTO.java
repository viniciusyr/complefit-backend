package com.complefit.complefit.auth.dto;

public record AuthResponseDTO(
        String accessToken,
        String refreshToken
) {}
