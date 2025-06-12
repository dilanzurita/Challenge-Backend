package com.challengeBP.accountMovements.infraestructure.input.adapter.rest.impl;

import com.challengeBP.accountMovements.application.dto.MovementDetailDTO;
import com.challengeBP.accountMovements.domain.input.MovementService;
import com.challengeBP.accountMovements.domain.model.Movement;
import com.challengeBP.accountMovements.infraestructure.output.adapter.mapper.MovementMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/movements")
@RequiredArgsConstructor
public class MovementController {

    private final MovementService movementService;
    private final MovementMapper movementMapper;

    @GetMapping
    public Flux<MovementDetailDTO> getAllMovements() {
        return movementService.getAll();
    }

    @GetMapping("/{id}")
    public Mono<MovementDetailDTO> getMovementById(@PathVariable Long id) {
        return movementService.getById(id);
    }

    @PostMapping
    public Mono<MovementDetailDTO> createMovement(@RequestBody Movement movement) {
        return movementService.create(movement);
    }

}
