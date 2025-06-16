package com.challengeBP.ClientAccount.infraestructure.output.adapter.impl;

import com.challengeBP.ClientAccount.application.exceptions.NotFoundException;
import com.challengeBP.ClientAccount.domain.model.Client;
import com.challengeBP.ClientAccount.domain.output.ClientPort;
import com.challengeBP.ClientAccount.infraestructure.output.entities.ClientEntity;
import com.challengeBP.ClientAccount.infraestructure.output.mappers.CustomerEntityMapper;
import com.challengeBP.ClientAccount.infraestructure.output.repositories.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class ClientServiceAdapter implements ClientPort {

    private final ClientRepository repository;
    private final CustomerEntityMapper mapper;
    @Override
    public Flux<Client> findAll() {
        return Flux.fromIterable(repository.findAll())
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Client> findById(Long id) {
        return Mono.justOrEmpty(repository.findById(id))
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Client> save(Client client) {
        return Mono.just(client)
                .map(mapper::toEntity)
                .map(repository::save)
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Client> update(Long id, Client client) {
        return Mono.just(id)
                .map(repository::findById)
                .flatMap(optionalEntity -> optionalEntity
                        .map(existing -> {
                            ClientEntity toUpdate = mapper.toEntity(client);
                            toUpdate.setId(id);
                            ClientEntity saved = repository.save(toUpdate);
                            return Mono.just(mapper.toDomain(saved));
                        })
                        .orElseGet(() -> Mono.error(new NotFoundException("Customer not found with id: " + id)))
                );
    }

    @Override
    public Mono<Void> delete(Long id) {
        return Mono.fromRunnable(() -> repository.deleteById(id));
    }
}
