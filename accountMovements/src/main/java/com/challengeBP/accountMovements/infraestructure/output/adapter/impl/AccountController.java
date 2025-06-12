package com.challengeBP.accountMovements.infraestructure.output.adapter.impl;

import com.challengeBP.accountMovements.domain.model.Account;
import com.challengeBP.accountMovements.domain.output.ClientAccountPort;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final ClientAccountPort clientAccountPort;
    @GetMapping("/{id}")
    public Mono<Account> getAccountById(@PathVariable Long id) {
        return clientAccountPort.findById(id);
    }

    @PutMapping("/{id}")
    public Mono<Account> updateAccount(@PathVariable Long id, @RequestBody Account account) {
        return clientAccountPort.update(account);
    }
}
