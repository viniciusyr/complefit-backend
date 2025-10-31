package com.complefit.complefit.auth.mapper;

import com.complefit.complefit.auth.dto.AuthResponseDTO;
import com.complefit.complefit.auth.dto.AuthUserDTO;
import com.complefit.complefit.user.domain.User;
import org.springframework.stereotype.Component;

@Component
public class AuthMapper {

    public static AuthResponseDTO toAuthResponse(String accessToken, String refreshToken, User user) {
        return new AuthResponseDTO(
                accessToken,
                refreshToken,
                toAuthUserDTO(user)
        );
    }

    private static AuthUserDTO toAuthUserDTO(User user) {
        return new AuthUserDTO(
                user.getId(),
                user.getFirstName() + " " + user.getLastName(),
                user.getEmail(),
                user.getRole().name()
        );
    }
}
