package ir.mehdi.mycleanarch.infrastructure.repositories;


import ir.mehdi.mycleanarch.infrastructure.entities.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaOrderRepository extends JpaRepository<OrderEntity, Long> {
}
