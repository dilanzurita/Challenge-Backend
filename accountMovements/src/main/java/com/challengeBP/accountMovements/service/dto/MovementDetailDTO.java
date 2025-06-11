package com.challengeBP.accountMovements.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovementDetailDTO {
    private LocalDateTime date;
    private String type;
    private Double value;
    private Double balance;
}