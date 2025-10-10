package com.complefit.complefit.workoutexercise.repository;

import com.complefit.complefit.workoutexercise.domain.WorkoutExercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface WorkoutExerciseRepository extends JpaRepository<WorkoutExercise, UUID> {
}
