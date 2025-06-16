package com.challengeBP.accountMovements;

import com.challengeBP.accountMovements.application.dto.MovementDetailDTO;
import com.challengeBP.accountMovements.domain.model.Account;
import com.challengeBP.accountMovements.domain.model.Movement;
import com.challengeBP.accountMovements.application.service.MovementServiceImpl;
import com.challengeBP.accountMovements.domain.output.ClientAccountPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
public class MovementServiceTest {

    @Mock
    private ClientAccountPort accountClientPort;

    @InjectMocks
    private MovementServiceImpl movementService;

    @Test
    void shouldThrowExceptionWhenBalanceIsInsufficient() {
        // Given
        Long accountId = 1L;
        Movement movement = new Movement();
        movement.setType("debito");
        movement.setValue(100.0);

        Account account = new Account();
        account.setId(accountId);
        account.setInitialBalance(50.0);

        movement.setAccount(account);

        Mockito.when(accountClientPort.findById(accountId)).thenReturn(Mono.just(account));

        // When
        Mono<MovementDetailDTO> result = movementService.create(movement);

        // Then
        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException &&
                        throwable.getMessage().equals("Saldo no disponible"))
                .verify();
    }

//    @Test
//    void shouldProcessCreditMovementSuccessfully() {
//        // Given
//        Long accountId = 1L;
//        Movement movement = new Movement();
//        movement.setType("credito");
//        movement.setValue(100.0);
//
//        Account account = new Account();
//        account.setId(accountId);
//        account.setInitialBalance(50.0);
//
//        movement.setAccount(account);
//
//
//        Mockito.when(accountClientPort.findById(accountId)).thenReturn(Mono.just(account));
//        Mockito.when(accountClientPort.update(account)).thenReturn(Mono.just(account));
//        Mono<Movement> result = movementService.create(movement);
//
//        StepVerifier.create(result)
//                .expectNextMatches(savedMovement -> savedMovement.getBalance() == 150.0)
//                .verifyComplete();
//    }
}
