package com.challengeBP.ClientAccount.infraestructure.output.repositories;

import com.challengeBP.ClientAccount.domain.model.Client;
import com.challengeBP.ClientAccount.infraestructure.output.entities.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<ClientEntity,Long> {
    //Se crea la interfaz sin embargo no se crean funciones ya que solo se ocupa las que Jpa proporciona por defailt
}
