package com.challengeBP.accountMovements.repository;

import com.challengeBP.accountMovements.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account,Long> {
    List<Account> findByClientId(Long clientId);
}
