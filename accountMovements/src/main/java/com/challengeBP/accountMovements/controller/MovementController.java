package com.challengeBP.accountMovements.controller;

import com.challengeBP.accountMovements.model.Movement;
import com.challengeBP.accountMovements.service.MovementService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/movements")
@RequiredArgsConstructor
public class MovementController {

    private final MovementService movementService;

    @GetMapping
    public Flux<Movement> getAllMovements() {
        return movementService.getAll();
    }

    @GetMapping("/{id}")
    public Mono<Movement> getMovementById(@PathVariable Long id) {
        return movementService.getById(id);
    }

    @PostMapping
    public Mono<Movement> createMovement(@RequestBody Movement movement) {
        return movementService.create(movement);
    }
}
