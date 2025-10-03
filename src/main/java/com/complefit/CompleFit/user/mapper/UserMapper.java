package com.complefit.CompleFit.user.mapper;

import com.complefit.CompleFit.user.domain.User;
import com.complefit.CompleFit.user.domain.UserRole;
import com.complefit.CompleFit.user.dto.UserRequestDTO;
import com.complefit.CompleFit.user.dto.UserResponseDTO;
import com.complefit.CompleFit.user.dto.UserUpdateDTO;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class UserMapper {

    private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public static User toEntity(UserRequestDTO dto) {
        User user = new User();
        user.setFirstName(dto.firstName());
        user.setLastName(dto.lastName());
        user.setEmail(dto.email());
        user.setPasswordHash(passwordEncoder.encode(dto.password()));
        user.setPhoneNumber(dto.phoneNumber());
        user.setBirthDate(dto.birthDate());
        user.setGender(dto.gender());
        user.setHeight(dto.height());
        user.setWeight(dto.weight());
        user.setRole(UserRole.USER);
        return user;
    }

    public static void updateEntity(User user, UserUpdateDTO dto) {
        if (dto.firstName() != null) user.setFirstName(dto.firstName());
        if (dto.lastName() != null) user.setLastName(dto.lastName());
        if (dto.phoneNumber() != null) user.setPhoneNumber(dto.phoneNumber());
        if (dto.birthDate() != null) user.setBirthDate(dto.birthDate());
        if (dto.gender() != null) user.setGender(dto.gender());
        if (dto.height() != null) user.setHeight(dto.height());
        if (dto.weight() != null) user.setWeight(dto.weight());
    }

    public static UserResponseDTO toResponse(User user) {
        return new UserResponseDTO(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getBirthDate(),
                user.getGender().name(),
                user.getHeight(),
                user.getWeight(),
                user.getRole()
        );
    }
}
