package com.complefit.CompleFit.auth.dto;

import lombok.Getter;

public record AuthResponseDTO(
        String accessToken,
        String refreshToken
) {}
