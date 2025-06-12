package com.challengeBP.accountMovements.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
public class AccountDTO {
    private Long id;
    private String number;
    private Double initialBalance;
    private Long clientId;
    private String type;

    private List<MovementDetailDTO> movements;
}
