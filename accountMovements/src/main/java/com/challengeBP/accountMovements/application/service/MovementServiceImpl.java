package com.challengeBP.accountMovements.application.service;

import com.challengeBP.accountMovements.application.dto.MovementDetailDTO;
import com.challengeBP.accountMovements.domain.model.Movement;
import com.challengeBP.accountMovements.infraestructure.output.adapter.repositories.MovementRepository;
import com.challengeBP.accountMovements.domain.input.MovementService;
import com.challengeBP.accountMovements.domain.output.ClientAccountPort;
import com.challengeBP.accountMovements.infraestructure.output.adapter.mapper.MovementMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class MovementServiceImpl implements MovementService{

    private final MovementRepository movementRepository;
    private final ClientAccountPort clientAccountPort;
    private final MovementMapper movementMapper;

    @Override
    public Flux<MovementDetailDTO> getAll() {
        return Flux.fromIterable(movementRepository.findAll())
                .map(movementMapper::toDto);
    }

    @Override
    public Mono<MovementDetailDTO> getById(Long id) {
        return Mono.justOrEmpty(movementRepository.findById(id))
                .map(movementMapper::toDto);
    }

    @Override
    public Mono<MovementDetailDTO> create(Movement movement) {
        if (movement.getValue() <= 0) {
            log.warn("Valor inválido: {}", movement.getValue());
            return Mono.error(new IllegalArgumentException("El valor debe ser > 0"));
        }

        return clientAccountPort.findById(movement.getAccount().getId())
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Cuenta inexistente")))
                .flatMap(account -> {

                    double newBalance = account.getInitialBalance();
                    log.info("Saldo antes: {}", newBalance);

                    if ("debito".equalsIgnoreCase(movement.getType())) {
                        if (movement.getValue() > newBalance) {
                            log.warn("Saldo insuficiente");
                            return Mono.error(new IllegalArgumentException("Saldo no disponible"));
                        }
                        newBalance -= movement.getValue();
                    } else if ("credito".equalsIgnoreCase(movement.getType())) {
                        newBalance += movement.getValue();
                    } else {
                        return Mono.error(new IllegalArgumentException("Tipo no válido"));
                    }

                    account.setInitialBalance(newBalance);
                    movement.setBalance(newBalance);
                    movement.setDate(LocalDateTime.now());
                    movement.setAccount(account);

                    return clientAccountPort.update(account)
                            .then(Mono.fromCallable(() -> movementRepository.save(movement)))
                            .map(movementMapper::toDto);
                });
    }
}
