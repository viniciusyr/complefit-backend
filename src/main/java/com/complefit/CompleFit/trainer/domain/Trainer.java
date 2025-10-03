package com.complefit.CompleFit.trainer.domain;

import com.complefit.CompleFit.user.domain.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "tb_trainers")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Trainer {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(nullable = false, unique = true, length = 20)
    private String cref;

    @Column(length = 255)
    private String specialty;

    @Column
    private Integer yearsOfExperience;

    @Column(length = 500)
    private String bio;
}
