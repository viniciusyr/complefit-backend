package com.complefit.CompleFit.student.domain;

import com.complefit.CompleFit.user.domain.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "students")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tb_user_id", nullable = false, unique = true)
    private User user;

    @Column(length = 255)
    private String goal;

    @Column(length = 100)
    private String level;

    @Column(length = 500)
    private String medicalRestrictions;
}
