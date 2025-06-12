package com.challengeBP.ClientAccount.domain.input;

import com.challengeBP.ClientAccount.domain.model.Client;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ClientServicePort {
    Flux<Client> getAll();
    Mono<Client> getById(Long id);
    Mono<Client> create(Client client);
    Mono<Client> update(Long id, Client client);
    Mono<Void> delete(Long id);
}
