package com.complefit.CompleFit.trainer.repository;

import com.complefit.CompleFit.trainer.domain.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface TrainerRepository extends JpaRepository<Trainer, UUID> {
    Optional<Trainer> findByCref(String cref);
    boolean existsByCref(String cref);
}
