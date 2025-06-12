package com.challengeBP.ClientAccount.application.service;

import com.challengeBP.ClientAccount.application.dto.AccountDTO;
import com.challengeBP.ClientAccount.domain.input.AccountServicePort;
import com.challengeBP.ClientAccount.domain.model.Account;
import com.challengeBP.ClientAccount.domain.output.AccountRepository;
import com.challengeBP.ClientAccount.infraestructure.input.adapter.mapper.AccountMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountServicePort {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    @Override
    public Flux<AccountDTO> getAll() {

        return Flux.fromIterable(accountRepository.findAll())
                .map(accountMapper::toDto);
    }

    @Override
    public Mono<AccountDTO> getById(Long id) {
        return Mono.justOrEmpty(accountRepository.findById(id))
                .map(accountMapper::toDto);
    }

    @Override
    public Mono<AccountDTO> create(AccountDTO dto) {
        Account entity = accountMapper.toEntity(dto);
        return Mono.just(accountRepository.save(entity))
                .map(accountMapper::toDto);
    }

    @Override
    public Mono<AccountDTO> update(Long id, AccountDTO dto) {
        return Mono.justOrEmpty(accountRepository.findById(id))
                .flatMap(existing -> {
                    // Copia los campos que vengan en el DTO sobre la entidad ya persistida
                    accountMapper.updateEntityFromDto(dto, existing);
                    return Mono.just(accountRepository.save(existing));
                })
                .map(accountMapper::toDto);
    }

    @Override
    public Mono<Void> delete(Long id) {
        return Mono.fromRunnable(() -> accountRepository.deleteById(id));
    }
}
