package ir.mehdi.mycleanarch.usecases.order;


import ir.mehdi.mycleanarch.domain.models.Identity;
import ir.mehdi.mycleanarch.domain.models.Order;
import ir.mehdi.mycleanarch.domain.repositories.OrderRepository;
import ir.mehdi.mycleanarch.infrastructure.exceptions.NotFoundException;
import ir.mehdi.mycleanarch.usecases.UseCase;
import lombok.Value;

public abstract class UpdateOrderUseCase extends UseCase<UpdateOrderUseCase.InputValues, UpdateOrderUseCase.OutputValues> {
    protected OrderRepository repository;

    protected UpdateOrderUseCase(OrderRepository repository) {
        this.repository = repository;
    }

    @Override
    public OutputValues execute(InputValues input) {
        final Identity id = input.getId();

        return this.repository
                .getById(id)
                .map(this::updateStatus)
                .map(this::persistAndReturn)
                .orElseThrow(() -> new NotFoundException("Order %s not found", id));
    }

    protected abstract Order updateStatus(Order order);

    private OutputValues persistAndReturn(Order order) {
        return new OutputValues(this.repository.persist(order));
    }

    @Value
    public static class InputValues implements UseCase.InputValues {
        private final Identity id;
    }

    @Value
    public static class OutputValues implements UseCase.OutputValues {
        private final Order order;
    }
}
