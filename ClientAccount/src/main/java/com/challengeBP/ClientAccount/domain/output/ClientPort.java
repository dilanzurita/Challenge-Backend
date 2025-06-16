package com.challengeBP.ClientAccount.domain.output;

import com.challengeBP.ClientAccount.domain.model.Client;
import com.challengeBP.ClientAccount.infraestructure.output.entities.ClientEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ClientPort {
    Flux<Client> findAll();
    Mono<Client> findById(Long id);
    Mono<Client> save(Client client);
    Mono<Client> update(Long id, Client client);
    Mono<Void> delete(Long id);
}
