package com.challengeBP.accountMovements.service;

import com.challengeBP.accountMovements.model.Movement;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MovementService {
    Flux<Movement> getAll();
    Mono<Movement> getById(Long id);
    Mono<Movement> create(Movement movement);
}
