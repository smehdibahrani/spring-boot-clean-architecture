package ir.mehdi.mycleanarch.infrastructure.repositories;


import ir.mehdi.mycleanarch.infrastructure.entities.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaCustomerRepository extends JpaRepository<CustomerEntity, Long> {
    boolean existsByEmail(String email);

    Optional<CustomerEntity> findByEmail(String email);
}
