package com.challengeBP.ClientAccount.infraestructure.input.adapter.mapper;

import com.challengeBP.ClientAccount.application.dto.AccountDTO;
import com.challengeBP.ClientAccount.domain.model.Account;
import org.mapstruct.*;
@Mapper(componentModel = "spring")
public interface AccountMapper {
    @Mapping(source = "client.id",   target = "clientId")
    @Mapping(source = "client.name", target = "clientName")
    AccountDTO toDto(Account entity);

    @InheritInverseConfiguration
    Account toEntity(AccountDTO dto);

    void updateEntityFromDto(AccountDTO dto, @MappingTarget Account entity);
}
