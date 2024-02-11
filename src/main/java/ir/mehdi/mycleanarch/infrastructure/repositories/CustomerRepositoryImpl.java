package ir.mehdi.mycleanarch.infrastructure.repositories;


import ir.mehdi.mycleanarch.infrastructure.exceptions.NotFoundException;
import ir.mehdi.mycleanarch.domain.models.Customer;
import ir.mehdi.mycleanarch.domain.repositories.CustomerRepository;
import ir.mehdi.mycleanarch.infrastructure.entities.CustomerEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class CustomerRepositoryImpl implements CustomerRepository {
    private final JpaCustomerRepository repository;

    private final ModelMapper modelMapper;

    public CustomerRepositoryImpl(JpaCustomerRepository repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Customer persist(Customer customer) {
        final CustomerEntity customerData = CustomerEntity.from(customer);
        return repository.save(customerData).fromThis();
    }

    @Override
    public boolean existsByEmail(String email) {
        return repository.existsByEmail(email);
    }

    @Override
    public Optional<CustomerEntity> findByEmail(String email) {
        return repository.findByEmail(email);//.orElseThrow(() -> new NotFoundException("customer not found"));
        //return Optional.of(modelMapper.map(customer, Customer.class));
    }

    @Override
    public Optional<CustomerEntity> findById(Long id) {
        return repository.findById(id);
        //return Optional.of(modelMapper.map(customer, CustomerEntity.class));

    }
}
