package com.challengeBP.ClientAccount.infraestructure.input.adapter.mapper;

import com.challengeBP.ClientAccount.application.dto.AccountDTO;
import com.challengeBP.ClientAccount.domain.model.Account;
import com.challengeBP.ClientAccount.infraestructure.input.adapter.dto.AccountRequestDto;
import org.mapstruct.*;
@Mapper(componentModel = "spring")
public interface AccountMapper {
    AccountDTO toDto(Account account);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", constant = "true")
    Account toDomain(AccountRequestDto accountRequestDto);
//    @Mapping(source = "client.id",   target = "clientId")
//    @Mapping(source = "client.name", target = "clientName")
//    AccountDTO toDto(Account entity);
//    Account toEntity(AccountDTO dto);
}
