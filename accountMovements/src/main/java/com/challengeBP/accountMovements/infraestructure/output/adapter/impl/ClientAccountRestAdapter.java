package com.challengeBP.accountMovements.infraestructure.output.adapter.impl;

import com.challengeBP.accountMovements.application.dto.AccountDTO;
import com.challengeBP.accountMovements.domain.model.Account;
import com.challengeBP.accountMovements.domain.output.ClientAccountPort;
import com.challengeBP.accountMovements.infraestructure.input.adapter.rest.mapper.ClientAccountMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ClientAccountRestAdapter implements ClientAccountPort {

    private final WebClient clientAccountWebClient;
    private final ClientAccountMapper mapper;

    @Override
    public Mono<Account> findById(Long id) {
        return clientAccountWebClient.get()
                .uri("/api/v1/accounts/{id}", id)
                .retrieve()
                .bodyToMono(AccountDTO.class)
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Account> update(Account account) {
        return clientAccountWebClient.put()
                .uri("/api/v1/accounts/{id}", account.getId())
                .bodyValue(mapper.toDto(account))
                .retrieve()
                .bodyToMono(AccountDTO.class)
                .map(mapper::toDomain);
    }
}
