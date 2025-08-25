package com.challengeBP.accountMovements.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account {
    private Long id;
    private String number;
    private String type;
    private BigDecimal initialBalance;
    private Boolean status;
    private Long clientId;
    private List<Movement> movements;
}
