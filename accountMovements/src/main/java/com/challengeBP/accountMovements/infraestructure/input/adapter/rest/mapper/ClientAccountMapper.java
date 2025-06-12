package com.challengeBP.accountMovements.infraestructure.input.adapter.rest.mapper;

import com.challengeBP.accountMovements.application.dto.AccountDTO;
import com.challengeBP.accountMovements.domain.model.Account;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClientAccountMapper {
    Account toDomain(AccountDTO dto);

    AccountDTO toDto(Account account);
}
