package com.challengeBP.ClientAccount.domain.output;

import com.challengeBP.ClientAccount.application.dto.AccountDTO;
import com.challengeBP.ClientAccount.domain.model.Account;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

public interface AccountPort {
    Flux<Account> findAll();
    Optional<Account> findById(Long id);
    Mono<Account> save(Account account);
    Mono<Void> delete(Long id);
    Optional<Account> findByNumber(String number);
}
