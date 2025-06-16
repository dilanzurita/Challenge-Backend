package com.challengeBP.accountMovements.application.service;

import com.challengeBP.accountMovements.application.dto.MovementDetailDTO;
import com.challengeBP.accountMovements.domain.exceptions.InsufficientBalanceException;
import com.challengeBP.accountMovements.domain.exceptions.InvalidAmountException;
import com.challengeBP.accountMovements.domain.exceptions.NotFoundException;
import com.challengeBP.accountMovements.domain.model.Movement;
import com.challengeBP.accountMovements.domain.output.MovementPort;
import com.challengeBP.accountMovements.infraestructure.output.adapter.repositories.MovementRepository;
import com.challengeBP.accountMovements.domain.input.MovementService;
import com.challengeBP.accountMovements.domain.output.ClientAccountPort;
import com.challengeBP.accountMovements.infraestructure.output.adapter.mapper.MovementMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static com.challengeBP.accountMovements.domain.enums.MovementType.CREDIT;
import static com.challengeBP.accountMovements.domain.enums.MovementType.DEBIT;

@Service
@RequiredArgsConstructor
@Slf4j
public class MovementServiceImpl implements MovementService{

    private final MovementPort movementRepository;
    private final ClientAccountPort clientAccountPort;
    private final MovementMapper movementMapper;

    @Override
    public Flux<Movement> getAll() {
        log.info("Getting All movements");
        return movementRepository.findAll();
    }

    @Override
    public Mono<Movement> getById(Long id) {
        log.info("Getting movements with id: {}",id);
        return movementRepository.findById(id);
    }
    @Override
    public Mono<Movement> create(Movement movement) {
        log.info("Starting to create movement: {}", movement);
        return validateMovement(movement)
                .flatMap(valid -> {
                    log.info("Movement validated successfully: {}", movement);
                    return processMovement(movement);
                })
                .doOnError(error -> log.error("Error during movement creation: {}", error.getMessage()))
                .doOnTerminate(() -> log.info("Movement creation process completed"));
    }

    private Mono<Movement> validateMovement(Movement movement) {
        log.info("Validating movement: {}", movement);

        if (movement.getValue().compareTo(BigDecimal.ZERO) <= 0) {
            log.warn("Invalid movement amount: {}. Amount must be greater than zero.", movement.getValue());
            return Mono.error(new InvalidAmountException("Movement amount must be greater than zero"));
        }

        if (movement.getType() == null) {
            log.warn("Missing movement type for: {}", movement);
            return Mono.error(new IllegalArgumentException("Movement type is required"));
        }

        log.info("Movement is valid: {}", movement);
        return Mono.just(movement);
    }

    private Mono<Movement> processMovement(Movement movement) {

        log.info("Processing movement with amount: {} and type: {}", movement.getValue(), movement.getType());
        movement.setDate(LocalDateTime.now());

        return clientAccountPort.findByNumber(movement.getAccountNumber())
                .switchIfEmpty(Mono.defer(() -> {
                    log.warn("Account not found with number: {}", movement.getAccountNumber());
                    return Mono.error(new NotFoundException("Account not found with id: " + movement.getAccountNumber()));
                }))
                .flatMap(account -> {

                    BigDecimal newBalance = switch (movement.getType()) {
                        case CREDIT -> account.getInitialBalance().add(movement.getValue());
                        case DEBIT -> {
                            if (account.getInitialBalance().compareTo(movement.getValue()) < 0) {
                                log.error("Insufficient balance: {}. Cannot process debit of amount: {}", account.getInitialBalance(), movement.getValue());
                                throw new InsufficientBalanceException("Insufficient balance");
                            }
                            yield account.getInitialBalance().subtract(movement.getValue());
                        }
                        default -> {
                            log.error("Invalid movement type: {}", movement.getType());
                            throw new IllegalArgumentException("Invalid movement type. Must be CREDIT or DEBIT");
                        }
                    };
                    log.info("Calculated new balance for account: {}", newBalance);
                    movement.setBalance(newBalance);
                    account.setInitialBalance(newBalance);

                    return clientAccountPort.update(account)
                            .doOnSuccess(savedAccount -> log.info("Account balance updated successfully: {}", savedAccount))
                            .then(movementRepository.save(movement))
                            .doOnSuccess(savedMovement -> log.info("Movement saved successfully: {}", savedMovement));
                });
    }
}
