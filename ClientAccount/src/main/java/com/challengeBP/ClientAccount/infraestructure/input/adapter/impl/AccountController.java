package com.challengeBP.ClientAccount.infraestructure.input.adapter.impl;

import com.challengeBP.ClientAccount.application.dto.AccountDTO;
import com.challengeBP.ClientAccount.domain.input.AccountServicePort;
import com.challengeBP.ClientAccount.domain.model.Account;
import com.challengeBP.ClientAccount.infraestructure.input.adapter.dto.AccountRequestDto;
import com.challengeBP.ClientAccount.infraestructure.input.adapter.mapper.AccountMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
@Slf4j
public class AccountController {

    private final AccountServicePort accountServicePort;
    private final AccountMapper mapper;

    @GetMapping
    public Flux<AccountDTO> getAllAccounts() {
        log.info("GET -> all accounts");
        return accountServicePort.getAll()
                .map(mapper::toDto);
    }

    @GetMapping("/{id}")
    public Optional<AccountDTO> getAccountById(@PathVariable Long id) {
        log.info("GET -> accounts by id: {}",id);
        return accountServicePort.getById(id)
                .map(mapper::toDto);
    }

    @PostMapping
    public Mono<AccountDTO> createAccount(@RequestBody AccountRequestDto requestDto) {
        log.info("POST -> createAccount {}",requestDto);
        Account account = mapper.toDomain(requestDto);
        return accountServicePort.create(account)
                .map(mapper::toDto);
    }

    @PutMapping("/{id}")
    public Optional<AccountDTO> updateAccount(@PathVariable Long id, @RequestBody AccountRequestDto requestDto) {
        Account account = mapper.toDomain(requestDto);
        return accountServicePort.update(id, account)
                .map(mapper::toDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteAccount(@PathVariable Long id) {
        log.info("DELETE -> delete account with id: {}", id);
        return accountServicePort.delete(id);
    }
}
