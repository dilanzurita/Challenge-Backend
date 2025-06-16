package com.challengeBP.ClientAccount.application.service;

import com.challengeBP.ClientAccount.application.dto.AccountDTO;
import com.challengeBP.ClientAccount.application.exceptions.ConflictException;
import com.challengeBP.ClientAccount.application.exceptions.NotFoundException;
import com.challengeBP.ClientAccount.domain.input.AccountServicePort;
import com.challengeBP.ClientAccount.domain.model.Account;
import com.challengeBP.ClientAccount.domain.output.AccountPort;
import com.challengeBP.ClientAccount.infraestructure.output.repositories.AccountRepository;
import com.challengeBP.ClientAccount.infraestructure.input.adapter.mapper.AccountMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountServiceImpl implements AccountServicePort {

    private final AccountPort accountPort;

    @Override
    public Flux<Account> getAll() {
        log.info("Getting All accounts");
        return accountPort.findAll();
    }

    @Override
    public Optional<Account> getById(Long id) {
        log.info("Getting account with id: {}",id);
        Optional<Account> result = accountPort.findById(id);
        result.ifPresentOrElse(
                acc -> log.info("Account found with ID: {}", id),
                () -> log.warn("Account not found with ID: {}", id)
        );
        return result;
    }

    @Override
    public Mono<Account> create(Account account) {
        log.info("Creating account: {}", account);
        try {
            Optional<Account> existing = accountPort.findByNumber(account.getNumber());
            if (existing.isPresent()) {
                log.warn("Conflict: Account already exists with number: {}", account.getNumber());
                return Mono.error(new ConflictException("Account already exists with number: " + account.getNumber()));
            }

            return accountPort.save(account)
                    .doOnSuccess(saved -> log.info("Account successfully created with ID: {}", saved.getId()))
                    .doOnError(e -> log.error("Error occurred while saving account: {}", e.getMessage(), e));

        } catch (Exception e) {
            log.error("Unexpected error during account creation: {}", e.getMessage(), e);
            return Mono.error(e);
        }
    }

    @Override
    public Optional<Account> update(Long id, Account account) {
        log.info("Updating account with id: {}", id);
        try {
            Optional<Account> existing = accountPort.findById(id);
            if (existing.isEmpty()) {
                log.warn("Account not found with id: {}", id);
                throw new NotFoundException("Account not found with id: " + id);
            }

            Optional<Account> duplicate = accountPort.findByNumber(account.getNumber());
            if (duplicate.isPresent() && !duplicate.get().getId().equals(id)) {
                log.warn("Duplicate number detected: {}", account.getNumber());
                throw new ConflictException("Number already used by another account");
            }

            account.setId(id);
            Account saved = accountPort.save(account).block();
            log.info("Account updated successfully with id: {}", saved.getId());
            return Optional.of(saved);

        } catch (Exception e) {
            log.error("Failed to update account with id: {}. Error: {}", id, e.getMessage(), e);
            return Optional.empty();
        }
    }

    @Override
    public Mono<Void> delete(Long id) {
        return Mono.fromRunnable(() -> accountPort.delete(id));
    }
}
