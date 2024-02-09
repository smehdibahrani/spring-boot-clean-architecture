package ir.mehdi.mycleanarch.domain.repositories;


import ir.mehdi.mycleanarch.domain.models.Customer;

import java.util.Optional;

public interface CustomerRepository {
    Customer persist(Customer customer);

    boolean existsByEmail(String email);

    Optional<Customer> findByEmail(String email);

    Optional<Customer> findById(Long id);
}
