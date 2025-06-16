package com.challengeBP.ClientAccount.application.service;

import com.challengeBP.ClientAccount.application.exceptions.NotFoundException;
import com.challengeBP.ClientAccount.domain.input.ClientServicePort;
import com.challengeBP.ClientAccount.domain.model.Client;
import com.challengeBP.ClientAccount.domain.output.ClientPort;
import com.challengeBP.ClientAccount.infraestructure.output.repositories.ClientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClientServiceImpl implements ClientServicePort {
    private final ClientPort clientPort;

    @Override
    public Flux<Client> getAll() {
        log.info("Getting all clients");
        return clientPort.findAll();
    }

    @Override
    public Mono<Client> getById(Long id) {
        log.info("Getting client by ID: {}", id);
        return clientPort.findById(id)
                .switchIfEmpty(Mono.defer(() -> {
                    log.warn("Client not found with id: {}", id);
                    return Mono.error(new NotFoundException("Client not found with id: " + id));
                }));
    }

    @Override
    public Mono<Client> create(Client client) {
        log.info("Creating new client: {}", client);
        return clientPort.save(client)
                .doOnSuccess(saved -> log.info("Client created with ID: {}", saved.getId()))
                .doOnError(e -> log.error("Error creating client: {}", e.getMessage(), e));
    }

    @Override
    public Mono<Client> update(Long id, Client client) {
        log.info("Updating client with ID: {}", id);
        return clientPort.findById(id)
                .switchIfEmpty(Mono.defer(() -> {
                    log.warn("Client not found for update with id: {}", id);
                    return Mono.error(new NotFoundException("Client not found with id: " + id));
                }))
                .flatMap(existing -> {
                    client.setId(id);
                    return clientPort.save(client)
                            .doOnSuccess(updated -> log.info("Client updated with ID: {}", updated.getId()))
                            .doOnError(e -> log.error("Error updating client: {}", e.getMessage(), e));
                });
    }

    @Override
    public Mono<Void> delete(Long id) {
        log.info("Deleting client with ID: {}", id);
        return clientPort.findById(id)
                .switchIfEmpty(Mono.defer(() -> {
                    log.warn("Client not found for deletion with id: {}", id);
                    return Mono.error(new NotFoundException("Client not found with id: " + id));
                }))
                .flatMap(existing -> clientPort.delete(id)
                        .doOnSuccess(v -> log.info("Client deleted with ID: {}", id))
                        .doOnError(e -> log.error("Error deleting client: {}", e.getMessage(), e))
                );
    }
}
