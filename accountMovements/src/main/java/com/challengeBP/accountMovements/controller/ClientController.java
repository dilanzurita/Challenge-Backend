package com.challengeBP.accountMovements.controller;

import com.challengeBP.accountMovements.model.Client;
import com.challengeBP.accountMovements.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class ClientController {
    private final ClientService clientService;

    @GetMapping
    public Flux<Client> getAllClients() {
        return clientService.getAll();
    }

    @GetMapping("/{id}")
    public Mono<Client> getClientById(@PathVariable Long id) {
        return clientService.getById(id);
    }

    @PostMapping
    public Mono<Client> createClient(@RequestBody Client client) {
        return clientService.create(client);
    }

    @PutMapping("/{id}")
    public Mono<Client> updateClient(@PathVariable Long id, @RequestBody Client client) {
        return clientService.update(id, client);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteClient(@PathVariable Long id) {
        return clientService.delete(id);
    }
}
