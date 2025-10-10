package com.complefit.complefit.workoutexercise.domain;

import com.complefit.complefit.workout.domain.Workout;
import jakarta.persistence.*;

import lombok.*;

import java.util.UUID;

@Entity
@Table(name="tb_workout_exercises")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class WorkoutExercise {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String exerciseId;

    private String exerciseName;

    private String description;
    private String videoUrl;
    private Integer sets;
    private Integer repetitions;
    private Double weight;
    private Integer restTimeSeconds;
    private Integer durationSeconds;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workout_id")
    private Workout workout;

    public int getTotalDuration() {
        int base = (durationSeconds != null && sets != null) ? durationSeconds * sets : 0;
        int rest = (restTimeSeconds != null && sets != null && sets > 1) ? restTimeSeconds * (sets - 1) : 0;
        return base + rest;
    }

}
