package com.challengeBP.ClientAccount.infraestructure.input.adapter.impl;

import com.challengeBP.ClientAccount.domain.input.ClientServicePort;
import com.challengeBP.ClientAccount.domain.model.Client;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class ClientController {
    private final ClientServicePort clientServicePort;

    @GetMapping
    public Flux<Client> getAllClients() {
        return clientServicePort.getAll();
    }

    @GetMapping("/{id}")
    public Mono<Client> getClientById(@PathVariable Long id) {
        return clientServicePort.getById(id);
    }

    @PostMapping
    public Mono<Client> createClient(@RequestBody Client client) {
        return clientServicePort.create(client);
    }

    @PutMapping("/{id}")
    public Mono<Client> updateClient(@PathVariable Long id, @RequestBody Client client) {
        return clientServicePort.update(id, client);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteClient(@PathVariable Long id) {
        return clientServicePort.delete(id);
    }
}
