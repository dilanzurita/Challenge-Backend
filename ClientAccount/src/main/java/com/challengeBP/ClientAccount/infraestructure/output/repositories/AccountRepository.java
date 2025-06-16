package com.challengeBP.ClientAccount.infraestructure.output.repositories;

import com.challengeBP.ClientAccount.domain.model.Account;
import com.challengeBP.ClientAccount.infraestructure.output.entities.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<AccountEntity,Long> {
    Optional<AccountEntity> findByNumber(String number);
    List<AccountEntity> findByClientId(Long clientId);
}
