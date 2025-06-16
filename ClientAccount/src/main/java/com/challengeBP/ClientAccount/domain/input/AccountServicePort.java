package com.challengeBP.ClientAccount.domain.input;

import com.challengeBP.ClientAccount.application.dto.AccountDTO;
import com.challengeBP.ClientAccount.domain.model.Account;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

public interface AccountServicePort {
    Flux<Account> getAll();
    Optional<Account> getById(Long id);
    Mono<Account> create(Account account);
    Optional<Account> update(Long id, Account account);
    Mono<Void> delete(Long id);
    Mono<Account> getByNumber(String number);
}
