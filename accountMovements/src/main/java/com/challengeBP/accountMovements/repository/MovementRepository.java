package com.challengeBP.accountMovements.repository;

import com.challengeBP.accountMovements.model.Movement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface MovementRepository extends JpaRepository<Movement,Long> {
    List<Movement> findByAccountIdAndDateBetween(Long accountId, LocalDateTime start, LocalDateTime end);
}
