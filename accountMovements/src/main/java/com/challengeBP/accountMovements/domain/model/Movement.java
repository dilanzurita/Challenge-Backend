package com.challengeBP.accountMovements.domain.model;

import com.challengeBP.accountMovements.domain.enums.MovementType;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Movement {
    private Long id;
    private LocalDateTime date;
    private MovementType type;
    private BigDecimal value;
    private BigDecimal balance;
    private String accountNumber;
}
