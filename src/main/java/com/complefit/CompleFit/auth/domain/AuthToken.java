package com.complefit.CompleFit.auth.domain;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(schema = "tb_auth_users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AuthToken {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false, unique = true)
    private String refreshToken;

    private LocalDateTime expiryDate;

    @Column(nullable = false)
    private UUID userId;

}
