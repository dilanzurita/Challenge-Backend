package com.challengeBP.accountMovements.infraestructure.input.adapter.rest.impl;

import com.challengeBP.accountMovements.application.dto.MovementDetailDTO;
import com.challengeBP.accountMovements.domain.input.MovementService;
import com.challengeBP.accountMovements.domain.model.Movement;
import com.challengeBP.accountMovements.infraestructure.input.adapter.rest.dto.MovementRequestDto;
import com.challengeBP.accountMovements.infraestructure.output.adapter.mapper.MovementMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/movements")
@RequiredArgsConstructor
@Slf4j
public class MovementController {

    private final MovementService movementService;
    private final MovementMapper movementMapper;

    @GetMapping
    public Flux<MovementDetailDTO> getAllMovements() {
        log.info("GET -> all accounts");
        return movementService.getAll()
                .map(movementMapper::toDto);
    }

    @GetMapping("/{id}")
    public Mono<MovementDetailDTO> getMovementById(@PathVariable Long id) {
        log.info("GET -> account by id: {}",id);
        return movementService.getById(id)
                .map(movementMapper::toDto);
    }

    @PostMapping
    public Mono<MovementDetailDTO> createMovement(@RequestBody MovementRequestDto dto) {
        Movement movement = movementMapper.dtoToDomain(dto);
        return movementService.create(movement)
                .map(movementMapper::toDto);
    }

}
