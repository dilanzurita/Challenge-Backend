package com.challengeBP.accountMovements.infraestructure.output.adapter.impl;

import com.challengeBP.accountMovements.domain.model.Movement;
import com.challengeBP.accountMovements.domain.output.MovementPort;
import com.challengeBP.accountMovements.infraestructure.output.adapter.mapper.MovementMapper;
import com.challengeBP.accountMovements.infraestructure.output.adapter.repositories.MovementRepository;
import com.challengeBP.accountMovements.infraestructure.output.entities.MovementEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Repository
@RequiredArgsConstructor
public class MovementAdapter implements MovementPort {

    private final MovementRepository repository;
    private final MovementMapper mapper;
    @Override
    public Flux<Movement> findAll() {
        return Flux.defer(() ->
                Flux.fromIterable(repository.findAll())
                        .map(mapper::toDomain)
        );
    }

    @Override
    public Mono<Movement> findById(Long id) {
        return Mono.fromCallable(() -> repository.findById(id))
                .flatMap(optional -> optional.map(entity ->
                                Mono.just(mapper.toDomain(entity))
                        ).orElseGet(Mono::empty)
                );
    }

    @Override
    public Mono<Movement> save(Movement movement) {
        return Mono.fromCallable(() -> {
            MovementEntity entity = mapper.toEntity(movement);
            return repository.save(entity);
        }).map(mapper::toDomain);
    }

    @Override
    public Mono<Void> deleteById(Long id) {
        return Mono.fromRunnable(() -> repository.deleteById(id));
    }

    @Override
    public Flux<Movement> findByAccountIdAndDateBetween(Long accountId, LocalDateTime start, LocalDateTime end) {
        return Flux.defer(() ->
                Flux.fromIterable(repository.findByAccountIdAndDateBetween(accountId, start, end))
                        .map(mapper::toDomain)
        );
    }
}
