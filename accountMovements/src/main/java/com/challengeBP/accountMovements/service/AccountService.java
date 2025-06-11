package com.challengeBP.accountMovements.service;

import com.challengeBP.accountMovements.model.Account;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AccountService {
    Flux<Account> getAll();
    Mono<Account> getById(Long id);
    Mono<Account> create(Account account);
    Mono<Account> update(Long id, Account account);
    Mono<Void> delete(Long id);
}
