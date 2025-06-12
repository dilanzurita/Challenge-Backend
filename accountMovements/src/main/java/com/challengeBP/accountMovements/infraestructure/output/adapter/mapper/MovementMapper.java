package com.challengeBP.accountMovements.infraestructure.output.adapter.mapper;

import com.challengeBP.accountMovements.application.dto.MovementDetailDTO;
import com.challengeBP.accountMovements.domain.model.Movement;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MovementMapper {
    MovementDetailDTO toDto(Movement movement);

    Movement toDomain(MovementDetailDTO dto);
}
