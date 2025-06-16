package com.challengeBP.ClientAccount.application.dto;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class AccountDTO {
    Long id;
    String number;
    String type;
    BigDecimal initialBalance;
    Boolean status;
    Long clientId;
}
