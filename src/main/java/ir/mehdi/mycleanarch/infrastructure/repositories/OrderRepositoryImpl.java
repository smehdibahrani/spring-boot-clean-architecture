package ir.mehdi.mycleanarch.infrastructure.repositories;


import ir.mehdi.mycleanarch.domain.models.Identity;
import ir.mehdi.mycleanarch.domain.models.Order;
import ir.mehdi.mycleanarch.domain.repositories.OrderRepository;
import ir.mehdi.mycleanarch.infrastructure.entities.OrderEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class OrderRepositoryImpl implements OrderRepository {
    private final JpaOrderRepository repository;

    public OrderRepositoryImpl(JpaOrderRepository repository) {
        this.repository = repository;
    }

    @Override
    public Order persist(Order order) {
        OrderEntity orderData = OrderEntity.from(order);

        return repository.save(orderData).fromThis();
    }

    @Override
    public Optional<Order> getById(Identity id) {
        return repository
                .findById(id.getNumber())
                .map(OrderEntity::fromThis);
    }
}
