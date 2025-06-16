package com.challengeBP.ClientAccount.infraestructure.output.adapter.impl;

import com.challengeBP.ClientAccount.domain.model.Account;
import com.challengeBP.ClientAccount.domain.output.AccountPort;
import com.challengeBP.ClientAccount.infraestructure.output.mappers.EntityMapper;
import com.challengeBP.ClientAccount.infraestructure.output.repositories.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Slf4j
public class AccountServiceAdapter implements AccountPort {
    private final AccountRepository accountRepository;
    private final EntityMapper entityMapper;

    @Override
    public Flux<Account> findAll() {
        return Flux.fromIterable(accountRepository.findAll())
                .doOnNext(entity -> log.info("Entidad recuperada: {}", entity))
                .map(entityMapper::toDomain);
    }

    @Override
    public Optional<Account> findById(Long id) {
        return accountRepository.findById(id)
                .map(entityMapper::toDomain);
    }

    @Override
    public Mono<Account> save(Account account) {
        return Mono.just(account)
                .map(entityMapper::toEntity)
                .map(accountRepository::save)
                .map(entityMapper::toDomain);
    }

    @Override
    public Mono<Void> delete(Long id) {
        accountRepository.deleteById(id);
        return Mono.empty();
    }

    @Override
    public Optional<Account> findByNumber(String number) {
        return accountRepository.findByNumber(number)
                .map(entityMapper::toDomain);
    }
}
