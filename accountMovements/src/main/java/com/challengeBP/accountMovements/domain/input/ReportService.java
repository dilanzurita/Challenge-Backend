package com.challengeBP.accountMovements.domain.input;

import com.challengeBP.accountMovements.application.dto.AccountDTO;
import com.challengeBP.accountMovements.domain.model.Account;
import org.springframework.data.jpa.repository.Query;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.List;

public interface ReportService {
    Mono<List<Account>> getAccountStatement(Long clientId, LocalDate start, LocalDate end);
}
