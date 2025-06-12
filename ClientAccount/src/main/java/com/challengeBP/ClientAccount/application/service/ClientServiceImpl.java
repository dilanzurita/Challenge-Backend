package com.challengeBP.ClientAccount.application.service;

import com.challengeBP.ClientAccount.domain.input.ClientServicePort;
import com.challengeBP.ClientAccount.domain.model.Client;
import com.challengeBP.ClientAccount.domain.output.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientServicePort {
    private final ClientRepository clientRepository;

    @Override
    public Flux<Client> getAll() {
        return Flux.fromIterable(clientRepository.findAll());
    }
    @Override
    public Mono<Client> getById(Long id) {
        return Mono.justOrEmpty(clientRepository.findById(id));
    }

    @Override
    public Mono<Client> create(Client client) {
        return Mono.just(clientRepository.save(client));
    }

    @Override
    public Mono<Client> update(Long id, Client client) {
        return Mono.justOrEmpty(clientRepository.findById(id))
                .map(existing -> {
                    existing.setName(client.getName());
                    existing.setGender(client.getGender());
                    existing.setIdentification(client.getIdentification());
                    existing.setAddress(client.getAddress());
                    existing.setPhone(client.getPhone());
                    existing.setPassword(client.getPassword());
                    existing.setStatus(client.getStatus());
                    return clientRepository.save(existing);
                });
    }

    @Override
    public Mono<Void> delete(Long id) {
        return Mono.fromRunnable(() -> clientRepository.deleteById(id));
    }
}
