package com.challengeBP.accountMovements.application.service;

import com.challengeBP.accountMovements.domain.input.ReportService;
import com.challengeBP.accountMovements.domain.model.Account;
import com.challengeBP.accountMovements.domain.model.Movement;
import com.challengeBP.accountMovements.domain.input.MovementRepository;
import com.challengeBP.accountMovements.application.dto.AccountDTO;
import com.challengeBP.accountMovements.application.dto.MovementDetailDTO;
import com.challengeBP.accountMovements.domain.output.ClientAccountPort;
import com.challengeBP.accountMovements.infraestructure.input.adapter.rest.mapper.ClientAccountMapper;
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
    private final ClientAccountPort accountClientPort;
    private final MovementRepository movementRepository;
    private final ClientAccountMapper clientAccountMapper;

    @Override
    public Mono<List<AccountDTO>> getAccountStatement(Long clientId, LocalDate start, LocalDate end) {
        return accountClientPort.findById(clientId)
                .flatMapMany(account -> {
                    List<Movement> filteredMovements = movementRepository.findByAccountIdAndDateBetween(
                            account.getId(), start.atStartOfDay(), end.atTime(LocalTime.MAX));

                    List<MovementDetailDTO> movementDTOs = filteredMovements.stream()
                            .map(m -> new MovementDetailDTO(m.getDate(), m.getType(), m.getValue(), m.getBalance()))
                            .toList();

                    AccountDTO dto = clientAccountMapper.toDto(account);
                    dto.setMovements(movementDTOs);

                    return Flux.just(dto);
                })
                .collectList();
    }
}
