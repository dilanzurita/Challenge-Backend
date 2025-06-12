package com.challengeBP.ClientAccount.application.dto;

import lombok.Value;

@Value
public class AccountDTO {
    Long id;
    String number;
    String type;
    Double initialBalance;
    Boolean status;
    Long clientId;
    String clientName;
}
