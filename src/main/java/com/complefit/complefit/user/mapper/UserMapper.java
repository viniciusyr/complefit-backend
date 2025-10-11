package com.complefit.complefit.user.mapper;

import com.complefit.complefit.user.domain.Gender;
import com.complefit.complefit.user.domain.User;
import com.complefit.complefit.user.domain.UserRole;
import com.complefit.complefit.user.dto.UserRequestDTO;
import com.complefit.complefit.user.dto.UserResponseDTO;
import com.complefit.complefit.user.dto.UserUpdateDTO;

import java.time.Instant;

public class UserMapper {

    public static User toEntity(UserRequestDTO dto) {
        User user = new User();
        user.setFirstName(dto.firstName());
        user.setLastName(dto.lastName());
        user.setEmail(dto.email());
        user.setPasswordHash(dto.password());
        user.setPhoneNumber(dto.phoneNumber());
        user.setBirthDate(dto.birthDate());
        user.setCpf(dto.cpf());
        user.setGender(dto.gender());
        user.setHeight(dto.height());
        user.setWeight(dto.weight());
        user.setRole(UserRole.USER);
        user.setCreatedAt(Instant.now());
        return user;
    }

    public static void updateEntity(User user, UserUpdateDTO dto) {
        if (dto.firstName() != null) user.setFirstName(dto.firstName());
        if (dto.lastName() != null) user.setLastName(dto.lastName());
        if (dto.phoneNumber() != null) user.setPhoneNumber(dto.phoneNumber());
        if (dto.birthDate() != null) user.setBirthDate(dto.birthDate());
        if (dto.cpf() != null) user.setCpf(dto.cpf());
        if (dto.gender() != null) user.setGender(Gender.valueOf(dto.gender()));
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
                user.getCpf(),
                user.getGender().name(),
                user.getHeight(),
                user.getWeight(),
                user.getRole(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }
}
