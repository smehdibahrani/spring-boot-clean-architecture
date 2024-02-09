package ir.mehdi.mycleanarch.usecases.order;


import ir.mehdi.mycleanarch.domain.models.Order;
import ir.mehdi.mycleanarch.domain.repositories.OrderRepository;

public class PayOrderUseCase extends UpdateOrderUseCase {
    public PayOrderUseCase(OrderRepository repository) {
        super(repository);
    }

    @Override
    protected Order updateStatus(Order order) {
        return repository.persist(order.pay());
    }
}
