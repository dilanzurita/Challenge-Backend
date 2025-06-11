package com.challengeBP.accountMovements.repository;

import com.challengeBP.accountMovements.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ClientRepository extends JpaRepository<Client,Long> {

}
