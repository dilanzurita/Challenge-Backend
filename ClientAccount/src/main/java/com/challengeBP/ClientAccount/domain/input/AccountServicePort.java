package com.challengeBP.ClientAccount.domain.input;

import com.challengeBP.ClientAccount.application.dto.AccountDTO;
import com.challengeBP.ClientAccount.domain.model.Account;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AccountServicePort {
    Flux<AccountDTO> getAll();
    Mono<AccountDTO> getById(Long id);
    Mono<AccountDTO> create(AccountDTO account);
    Mono<AccountDTO> update(Long id, AccountDTO account);
    Mono<Void> delete(Long id);
}
