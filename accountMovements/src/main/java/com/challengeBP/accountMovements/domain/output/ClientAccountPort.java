package com.challengeBP.accountMovements.domain.output;

import com.challengeBP.accountMovements.domain.model.Account;
import com.challengeBP.accountMovements.domain.model.Movement;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;

public interface ClientAccountPort {
    Mono<Account> findById(Long id);
    Mono<Account> update(Account account);
}
