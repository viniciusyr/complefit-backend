package com.complefit.complefit.exercise.repository;

import com.complefit.complefit.exercise.domain.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, UUID> {
    List<Exercise> findByNameContainingIgnoreCase(String name);
    List<Exercise> findByCategory(String category);
    List<Exercise> findByMuscleGroup(String muscleGroup);
    List<Exercise> findByEquipment(String equipment);
    List<Exercise> findByDifficulty(String difficulty);
}
