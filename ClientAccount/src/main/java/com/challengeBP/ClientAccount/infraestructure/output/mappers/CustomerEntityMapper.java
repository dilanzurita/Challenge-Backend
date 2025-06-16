package com.challengeBP.ClientAccount.infraestructure.output.mappers;

import com.challengeBP.ClientAccount.domain.model.Client;
import com.challengeBP.ClientAccount.infraestructure.output.entities.ClientEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerEntityMapper {
    Client toDomain(ClientEntity entity);

    ClientEntity toEntity(Client domain);
}
