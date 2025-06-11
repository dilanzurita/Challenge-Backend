package com.challengeBP.accountMovements.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDTO {
    private String accountNumber;
    private String accountType;
    private Double currentBalance;

    private List<MovementDetailDTO> movements;
}
