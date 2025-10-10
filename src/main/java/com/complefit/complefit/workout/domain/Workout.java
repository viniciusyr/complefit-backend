package com.complefit.complefit.workout.domain;

import com.complefit.complefit.user.domain.User;
import com.complefit.complefit.workoutexercise.domain.WorkoutExercise;
import jakarta.persistence.*;
import jdk.jfr.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tb_workouts")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Workout {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String title;
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trainer_id", nullable = true)
    private User trainer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private User student;

    @OneToMany(mappedBy = "workout", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WorkoutExercise> exercises = new ArrayList<>();

    private int totalDuration;
    private String notes;

    @Enumerated(EnumType.STRING)
    private WorkoutVisibility visibility;

    @Timestamp
    private Instant createdAt;

    @Timestamp
    private Instant updatedAt;

    public Workout(String title, String description, User trainer, User student, List<WorkoutExercise> exercises, String notes, String visibility ){
        this.title = title;
        this.description = description;
        this.trainer = trainer;
        this.student = student;
        this.exercises = exercises;
        this.notes = notes;
        this.visibility = WorkoutVisibility.valueOf(visibility);
    }


    @PrePersist
    @PreUpdate
    public void calculateTotalDuration() {
        this.totalDuration = exercises.stream()
                .mapToInt(WorkoutExercise::getTotalDuration)
                .sum();
    }

}

