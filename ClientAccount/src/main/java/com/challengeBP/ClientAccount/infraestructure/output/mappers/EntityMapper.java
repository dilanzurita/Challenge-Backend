package com.challengeBP.ClientAccount.infraestructure.output.mappers;

import com.challengeBP.ClientAccount.domain.model.Account;
import com.challengeBP.ClientAccount.infraestructure.output.entities.AccountEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EntityMapper {

    @Mapping(source = "client.id",   target = "clientId")
    Account toDomain(AccountEntity accountEntity);

    AccountEntity toEntity(Account account);
}
