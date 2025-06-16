package com.challengeBP.accountMovements.infraestructure.output.adapter.repositories;

import com.challengeBP.accountMovements.domain.model.Movement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface MovementRepository extends JpaRepository<Movement,Long> {
    List<Movement> findByAccountIdAndDateBetween(Long accountId, LocalDateTime start, LocalDateTime end);
}
