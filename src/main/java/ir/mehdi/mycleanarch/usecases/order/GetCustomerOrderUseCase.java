package ir.mehdi.mycleanarch.usecases.order;


import ir.mehdi.mycleanarch.domain.models.Customer;
import ir.mehdi.mycleanarch.domain.models.Identity;
import ir.mehdi.mycleanarch.domain.models.Order;
import ir.mehdi.mycleanarch.usecases.UseCase;
import lombok.Value;

public class GetCustomerOrderUseCase extends UseCase<GetCustomerOrderUseCase.InputValues, GetCustomerOrderUseCase.OutputValues> {
    private final GetOrderUseCase getOrderUseCase;

    public GetCustomerOrderUseCase(GetOrderUseCase getOrderUseCase) {
        this.getOrderUseCase = getOrderUseCase;
    }

    @Override
    public OutputValues execute(InputValues input) {
        Order order = getOrderUseCase
                .execute(new GetOrderUseCase.InputValues(input.getId()))
                .getOrder();

        return new OutputValues(order.getCustomer());
    }

    @Value
    public static class InputValues implements UseCase.InputValues {
        private final Identity id;
    }

    @Value
    public static class OutputValues implements UseCase.OutputValues {
        private final Customer customer;
    }
}
