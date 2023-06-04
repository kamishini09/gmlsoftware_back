package com.example.alianza.infrastructure.H2.Repository;

import com.example.alianza.infrastructure.H2.Entity.ClientEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ClientCrudRepository extends CrudRepository<ClientEntity,Long> {

    ClientEntity findBySharedKey(String sharedKey);
    Optional<ClientEntity> findByName(String name);
}
