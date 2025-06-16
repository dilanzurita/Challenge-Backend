package com.challengeBP.accountMovements.domain.input;

import com.challengeBP.accountMovements.application.dto.MovementDetailDTO;
import com.challengeBP.accountMovements.domain.model.Movement;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MovementService {
    Flux<Movement> getAll();
    Mono<Movement> getById(Long id);
    Mono<Movement> create(Movement movement);
}
