package com.complefit.CompleFit.user.domain;

import jakarta.persistence.*;
import jdk.jfr.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tb_users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String firstName;
    private String lastName;

    @Column(unique = true, nullable = false)
    private String email;

    private String passwordHash;

    private String phoneNumber;
    private LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private Double height;
    private Double weight;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Timestamp
    private Instant createdAt;

    @Timestamp
    private Instant updatedAt;

}

