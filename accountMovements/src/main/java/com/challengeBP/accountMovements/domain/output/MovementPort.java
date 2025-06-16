package com.challengeBP.accountMovements.domain.output;

import com.challengeBP.accountMovements.domain.model.Movement;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

public interface MovementPort {
    Flux<Movement> findAll();

    Mono<Movement> findById(Long id);

    Mono<Movement> save(Movement movement);

    Mono<Void> deleteById(Long id);
    Flux<Movement> findByAccountIdAndDateBetween(Long accountId, LocalDateTime start, LocalDateTime end);
}
