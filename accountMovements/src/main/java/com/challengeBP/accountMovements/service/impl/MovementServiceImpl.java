package com.challengeBP.accountMovements.service.impl;

import com.challengeBP.accountMovements.model.Account;
import com.challengeBP.accountMovements.model.Movement;
import com.challengeBP.accountMovements.repository.AccountRepository;
import com.challengeBP.accountMovements.repository.MovementRepository;
import com.challengeBP.accountMovements.service.MovementService;
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
    private final AccountRepository accountRepository;

    @Override
    public Flux<Movement> getAll() {
        return Flux.fromIterable(movementRepository.findAll());
    }

    @Override
    public Mono<Movement> getById(Long id) {
        return Mono.justOrEmpty(movementRepository.findById(id));
    }

    @Override
    public Mono<Movement> create(Movement movement) {
        if (movement.getValue() <= 0) {
            log.warn("Intento de registrar movimiento con valor inválido: {}", movement.getValue());
            return Mono.error(new IllegalArgumentException("El valor del movimiento debe ser mayor que cero"));
        }

        return Mono.justOrEmpty(accountRepository.findById(movement.getAccount().getId()))
                .flatMap(account -> {
                    double newBalance = account.getInitialBalance();
                    log.info("Cuenta antes del movimiento - ID: {}, Saldo actual: {}", account.getId(), newBalance);

                    if ("debito".equalsIgnoreCase(movement.getType())) {
                        if (movement.getValue() > newBalance) {
                            log.warn("Saldo insuficiente para débito. Cuenta ID: {}, Saldo: {}, Monto: {}", account.getId(), newBalance, movement.getValue());
                            return Mono.error(new IllegalArgumentException("Saldo no disponible"));
                        }
                        newBalance -= movement.getValue();
                    } else if ("credito".equalsIgnoreCase(movement.getType())) {
                        newBalance += movement.getValue();
                    } else {
                        log.error("Tipo de movimiento no válido: {}", movement.getType());
                        return Mono.error(new IllegalArgumentException("Tipo de movimiento no válido"));
                    }

                    account.setInitialBalance(newBalance);
                    movement.setBalance(newBalance);
                    movement.setDate(LocalDateTime.now());
                    movement.setAccount(account);

                    log.info("Movimiento registrado - Tipo: {}, Valor: {}, Nuevo saldo: {}", movement.getType(), movement.getValue(), newBalance);

                    accountRepository.save(account);
                    return Mono.just(movementRepository.save(movement));
                });
    }
}
