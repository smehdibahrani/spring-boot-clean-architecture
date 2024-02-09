package ir.mehdi.mycleanarch.usecases.order;


import ir.mehdi.mycleanarch.domain.models.Order;
import ir.mehdi.mycleanarch.domain.repositories.OrderRepository;

public class DeliveryOrderUseCase extends UpdateOrderUseCase {
    public DeliveryOrderUseCase(OrderRepository repository) {
        super(repository);
    }

    @Override
    protected Order updateStatus(Order order) {
        order.delivery();

        return repository.persist(order.delivery());
    }
}
