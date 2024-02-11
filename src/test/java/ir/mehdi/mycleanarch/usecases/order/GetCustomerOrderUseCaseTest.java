package ir.mehdi.mycleanarch.usecases.order;


import ir.mehdi.mycleanarch.domain.models.Customer;
import ir.mehdi.mycleanarch.domain.models.Order;
import ir.mehdi.mycleanarch.infrastructure.entities.TestCoreEntityGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
public class GetCustomerOrderUseCaseTest {

    @InjectMocks
    private GetCustomerOrderUseCase useCase;

    @Mock
    private GetOrderUseCase getOrderUseCase;

    @Test
    public void executeReturnsOrderCustomer() {
        // given
        Order order = TestCoreEntityGenerator.randomOrder();
        Customer expected = order.getCustomer();

        GetCustomerOrderUseCase.InputValues input =
                new GetCustomerOrderUseCase.InputValues(order.getId());

        GetOrderUseCase.InputValues orderByIdInput =
                new GetOrderUseCase.InputValues(order.getId());

        GetOrderUseCase.OutputValues orderByIdOutput =
                new GetOrderUseCase.OutputValues(order);

        // and
        doReturn(orderByIdOutput)
                .when(getOrderUseCase)
                .execute(eq(orderByIdInput));

        // when
        Customer actual = useCase.execute(input).getCustomer();

        // then
        assertThat(actual).isEqualTo(expected);
    }
}