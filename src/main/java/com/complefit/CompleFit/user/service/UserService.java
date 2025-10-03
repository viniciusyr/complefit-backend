package com.complefit.CompleFit.user.service;

import com.complefit.CompleFit.user.domain.Gender;
import com.complefit.CompleFit.user.domain.User;
import com.complefit.CompleFit.user.domain.UserRole;
import com.complefit.CompleFit.user.dto.UserUpdateDTO;
import com.complefit.CompleFit.user.exception.UserException;
import com.complefit.CompleFit.user.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public User createUser(User user) {
        if (!userRepository.existsByEmail(user.getEmail())) {
            throw UserException.emailAlreadyExists(user.getEmail());
        }

        user.setPasswordHash(passwordEncoder.encode(user.getPasswordHash()));
        user.setRole(UserRole.USER);
        return userRepository.save(user);
    }

    public Optional<User> getUserById(UUID id) {
        return userRepository.findById(id);
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User updateUser(UUID id, UserUpdateDTO dto) {
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

                    return userRepository.save(user);
                })
                .orElseThrow(() -> UserException.notFound(id));
    }

    public void deleteUser(UUID id) {
        userRepository.deleteById(id);
    }
}
