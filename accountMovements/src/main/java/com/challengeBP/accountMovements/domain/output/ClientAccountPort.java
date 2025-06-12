package com.challengeBP.accountMovements.domain.output;

import com.challengeBP.accountMovements.domain.model.Account;
import reactor.core.publisher.Mono;

public interface ClientAccountPort {
    Mono<Account> findById(Long id);           // GET /accounts/{id}
    Mono<Account> update(Account account);
}
