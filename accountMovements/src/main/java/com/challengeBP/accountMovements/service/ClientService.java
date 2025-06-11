package com.challengeBP.accountMovements.service;

import com.challengeBP.accountMovements.model.Client;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ClientService {
    Flux<Client> getAll();
    Mono<Client> getById(Long id);
    Mono<Client> create(Client client);
    Mono<Client> update(Long id, Client client);
    Mono<Void> delete(Long id);
}
