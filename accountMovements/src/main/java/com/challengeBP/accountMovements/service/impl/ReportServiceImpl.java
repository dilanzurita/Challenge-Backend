package com.challengeBP.accountMovements.service.impl;

import com.challengeBP.accountMovements.model.Movement;
import com.challengeBP.accountMovements.repository.AccountRepository;
import com.challengeBP.accountMovements.repository.MovementRepository;
import com.challengeBP.accountMovements.service.dto.AccountDTO;
import com.challengeBP.accountMovements.service.dto.MovementDetailDTO;
import com.challengeBP.accountMovements.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {
    private final AccountRepository accountRepository;
    private final MovementRepository movementRepository;
    @Override
    public Mono<List<AccountDTO>> getAccountStatement(Long clientId, LocalDate start, LocalDate end) {
        return Mono.fromCallable(() -> accountRepository.findByClientId(clientId))
                .flatMapMany(Flux::fromIterable)
                .flatMap(account -> {
                    List<Movement> filteredMovements = movementRepository.findByAccountIdAndDateBetween(
                            account.getId(), start.atStartOfDay(), end.atTime(LocalTime.MAX));

                    List<MovementDetailDTO> movementDTOs = filteredMovements.stream()
                            .map(m -> new MovementDetailDTO(m.getDate(), m.getType(), m.getValue(), m.getBalance()))
                            .toList();

                    AccountDTO dto = new AccountDTO(
                            account.getNumber(),
                            account.getType(),
                            account.getInitialBalance(),
                            movementDTOs
                    );

                    return Mono.just(dto);
                })
                .collectList();
    }
}
