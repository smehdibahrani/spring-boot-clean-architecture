package ir.mehdi.mycleanarch.usecases;


import ir.mehdi.mycleanarch.domain.models.Identity;
import ir.mehdi.mycleanarch.domain.models.Order;
import ir.mehdi.mycleanarch.domain.repositories.OrderRepository;
import ir.mehdi.mycleanarch.infrastructure.entities.TestCoreEntityGenerator;
import ir.mehdi.mycleanarch.infrastructure.exceptions.NotFoundException;
import ir.mehdi.mycleanarch.usecases.order.GetOrderUseCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
public class GetOrderUseCaseTest {

    @InjectMocks
    private GetOrderUseCase useCase;

    @Mock
    private OrderRepository repository;

    @Test
    public void executeThrowExceptionWhenOrderIsNotFound() {
        // given
        Identity id = TestCoreEntityGenerator.randomId();
        GetOrderUseCase.InputValues input = new GetOrderUseCase.InputValues(id);

        // and
        doReturn(Optional.empty())
                .when(repository)
                .getById(eq(id));

        // then
        assertThatThrownBy(() -> useCase.execute(input))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Order " + id.getNumber() + " not found");
    }

    @Test
    public void executeReturnsOrderWhenOrderIsFound() {
        // given
        Order expected = TestCoreEntityGenerator.randomOrder();
        GetOrderUseCase.InputValues input = new GetOrderUseCase.InputValues(expected.getId());

        // and
        doReturn(Optional.of(expected))
                .when(repository)
                .getById(eq(expected.getId()));

        // when
        Order actual = useCase.execute(input).getOrder();

        // then
        assertThat(actual).isEqualTo(expected);
    }
}