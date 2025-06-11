package com.challengeBP.accountMovements.service.impl;

import com.challengeBP.accountMovements.model.Account;
import com.challengeBP.accountMovements.repository.AccountRepository;
import com.challengeBP.accountMovements.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    @Override
    public Flux<Account> getAll() {
        return Flux.fromIterable(accountRepository.findAll());
    }

    @Override
    public Mono<Account> getById(Long id) {
        return Mono.justOrEmpty(accountRepository.findById(id));
    }

    @Override
    public Mono<Account> create(Account account) {
        return Mono.just(accountRepository.save(account));
    }

    @Override
    public Mono<Account> update(Long id, Account account) {
        return Mono.justOrEmpty(accountRepository.findById(id))
                .map(existing -> {
                    existing.setNumber(account.getNumber());
                    existing.setType(account.getType());
                    existing.setInitialBalance(account.getInitialBalance());
                    existing.setStatus(account.getStatus());
                    existing.setClient(account.getClient());
                    return accountRepository.save(existing);
                });
    }

    @Override
    public Mono<Void> delete(Long id) {
        return Mono.fromRunnable(() -> accountRepository.deleteById(id));
    }
}
