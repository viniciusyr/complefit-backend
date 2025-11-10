package com.complefit.complefit.exercise.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "tb_exercises")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Exercise {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 150)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(length = 100)
    private String category;

    @Column(length = 100)
    private String muscleGroup;

    @Column(length = 100)
    private String equipment;

    @Column(length = 50)
    private String difficulty;

    @Column(length = 255)
    private String videoUrl;

    @Column(length = 255)
    private String imageUrl;
}
