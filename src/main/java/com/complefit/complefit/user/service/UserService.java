package com.complefit.complefit.user.service;

import com.complefit.complefit.user.domain.Gender;
import com.complefit.complefit.user.domain.User;
import com.complefit.complefit.user.domain.UserStatus;
import com.complefit.complefit.user.dto.UserRequestDTO;
import com.complefit.complefit.user.dto.UserResponseDTO;
import com.complefit.complefit.user.dto.UserUpdateDTO;
import com.complefit.complefit.user.exception.UserException;
import com.complefit.complefit.user.mapper.UserMapper;
import com.complefit.complefit.user.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public UserResponseDTO createUser(UserRequestDTO requestDTO) {
        if (userRepository.existsByEmail(requestDTO.email())) {
            throw UserException.emailAlreadyExists(requestDTO.email());
        }

        User user = UserMapper.toEntity(requestDTO);

        user.setPasswordHash(passwordEncoder.encode(user.getPasswordHash()));

        userRepository.save(user);

        return UserMapper.toResponse(user);
    }

    public UserResponseDTO getAuthenticatedUserProfile(User authenticatedUser){
        User user = userRepository.findById(authenticatedUser.getId())
                .orElseThrow(() -> UserException.notFound(authenticatedUser));

        return UserMapper.toResponse(user);
    }

    public UserResponseDTO getUserById(UUID id) {
         User user = userRepository.findById(id).orElseThrow(() -> UserException.notFound(id));
         return UserMapper.toResponse(user);
    }

    public UserResponseDTO getUserByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> UserException.emailNotFound(email));
        return UserMapper.toResponse(user);
    }

    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll().stream().map(UserMapper::toResponse).toList();
    }

    public UserResponseDTO updateUser(UUID id, UserUpdateDTO dto) {
        return userRepository.findById(id)
                .map(user -> {
                    if (dto.firstName() != null && !dto.firstName().isBlank()) {
                        user.setFirstName(dto.firstName());
                    }

                    if (dto.lastName() != null && !dto.lastName().isBlank()) {
                        user.setLastName(dto.lastName());
                    }

                    if (dto.phoneNumber() != null && !dto.phoneNumber().isBlank()) {
                        user.setPhoneNumber(dto.phoneNumber());
                    }

                    if (dto.height() != null && dto.height() > 0) {
                        user.setHeight(dto.height());
                    }

                    if (dto.weight() != null && dto.weight() > 0) {
                        user.setWeight(dto.weight());
                    }

                    if (dto.gender() != null) {
                        user.setGender(Gender.valueOf(dto.gender()));
                    }

                    if (dto.birthDate() != null) {
                        if (dto.birthDate().isAfter(LocalDate.now())) {
                            throw UserException.invalidBirthDate();
                        }
                        user.setBirthDate(dto.birthDate());
                    }

                    user.setUpdatedAt(Instant.now());

                    userRepository.save(user);

                    return UserMapper.toResponse(user);
                })
                .orElseThrow(() -> UserException.notFound(id));
    }

    public UserResponseDTO inactiveUSer(UUID id) {
        return userRepository.findById(id).map(user -> {
            if(!user.getStatus().name().equals("ACTIVE")){
                throw UserException.inactiveUserStatus();
            }
            user.setStatus(UserStatus.INACTIVE);
            return UserMapper.toResponse(user);
        }).orElseThrow(() -> UserException.notFound(id));
    }
}
