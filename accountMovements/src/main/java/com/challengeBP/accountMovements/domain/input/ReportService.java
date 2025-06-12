package com.challengeBP.accountMovements.domain.input;

import com.challengeBP.accountMovements.application.dto.AccountDTO;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.List;

public interface ReportService {
    Mono<List<AccountDTO>> getAccountStatement(Long clientId, LocalDate start, LocalDate end);
}
