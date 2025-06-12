package com.challengeBP.ClientAccount.domain.output;

import com.challengeBP.ClientAccount.domain.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client,Long> {
}
