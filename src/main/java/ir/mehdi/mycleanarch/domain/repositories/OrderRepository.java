package ir.mehdi.mycleanarch.domain.repositories;


import ir.mehdi.mycleanarch.domain.models.Identity;
import ir.mehdi.mycleanarch.domain.models.Order;

import java.util.Optional;

public interface OrderRepository {

    Order persist(Order order);

    Optional<Order> getById(Identity id);
}
