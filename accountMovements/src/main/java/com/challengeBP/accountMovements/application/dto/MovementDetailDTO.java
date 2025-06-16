package com.challengeBP.accountMovements.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovementDetailDTO {
    private LocalDateTime date;
    private String type;
    private BigDecimal value;
    private BigDecimal balance;
}