package com.challengeBP.accountMovements.infraestructure.output.adapter.mapper;

import com.challengeBP.accountMovements.application.dto.MovementDetailDTO;
import com.challengeBP.accountMovements.domain.model.Movement;
import com.challengeBP.accountMovements.infraestructure.input.adapter.rest.dto.MovementRequestDto;
import com.challengeBP.accountMovements.infraestructure.output.entities.MovementEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MovementMapper {
    MovementDetailDTO toDto(Movement movement);

    Movement toDomain(MovementEntity entity);

    MovementEntity toEntity(Movement domain);

    Movement dtoToDomain(MovementRequestDto dto);
}
