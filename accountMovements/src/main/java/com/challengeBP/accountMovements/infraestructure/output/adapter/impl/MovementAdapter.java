package com.challengeBP.accountMovements.infraestructure.output.adapter.impl;

import com.challengeBP.accountMovements.domain.model.Movement;
import com.challengeBP.accountMovements.domain.output.MovementPort;
import com.challengeBP.accountMovements.infraestructure.output.adapter.repositories.MovementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Repository
@RequiredArgsConstructor
public class MovementAdapter implements MovementPort {

    private final MovementRepository repository;
    @Override
    public Flux<Movement> findAll() {
        return null;
    }

    @Override
    public Mono<Movement> findById(Long id) {
        return null;
    }

    @Override
    public Mono<Movement> save(Movement movement) {
        return null;
    }

    @Override
    public Mono<Void> deleteById(Long id) {
        return null;
    }

    @Override
    public Flux<Movement> findByAccountIdAndDateBetween(Long accountId, LocalDateTime start, LocalDateTime end) {
        return null;
    }
}
