package com.challengeBP.ClientAccount.infraestructure.input.adapter.impl;

import com.challengeBP.ClientAccount.application.dto.AccountDTO;
import com.challengeBP.ClientAccount.domain.input.AccountServicePort;
import com.challengeBP.ClientAccount.domain.model.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountServicePort accountServicePort;

    @GetMapping
    public Flux<AccountDTO> getAllAccounts() {
        return accountServicePort.getAll();
    }

    @GetMapping("/{id}")
    public Mono<AccountDTO> getAccountById(@PathVariable Long id) {
        return accountServicePort.getById(id);
    }

    @PostMapping
    public Mono<AccountDTO> createAccount(@RequestBody AccountDTO account) {
        return accountServicePort.create(account);
    }

    @PutMapping("/{id}")
    public Mono<AccountDTO> updateAccount(@PathVariable Long id, @RequestBody AccountDTO account) {
        return accountServicePort.update(id, account);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteAccount(@PathVariable Long id) {
        return accountServicePort.delete(id);
    }
}
