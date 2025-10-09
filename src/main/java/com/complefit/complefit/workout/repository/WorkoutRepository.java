package com.complefit.complefit.workout.repository;

import com.complefit.complefit.workout.domain.Workout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface WorkoutRepository extends JpaRepository<Workout, UUID> {

    List<Workout> findByTrainerId(UUID trainerId);
    List<Workout> findByStudentId(UUID studentId);
}
