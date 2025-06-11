package com.challengeBP.accountMovements;

import com.challengeBP.accountMovements.model.Account;
import com.challengeBP.accountMovements.model.Movement;
import com.challengeBP.accountMovements.repository.AccountRepository;
import com.challengeBP.accountMovements.repository.MovementRepository;
import com.challengeBP.accountMovements.service.impl.MovementServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class MovementServiceTest {
    @Mock
    private MovementRepository movementRepository;

    @Mock
    private AccountRepository accountRepository;

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

        Mockito.when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));

        // When
        Mono<Movement> result = movementService.create(movement);

        // Then
        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException &&
                        throwable.getMessage().equals("Saldo no disponible"))
                .verify();
    }
}
