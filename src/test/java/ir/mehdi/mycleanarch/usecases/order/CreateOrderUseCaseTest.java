package ir.mehdi.mycleanarch.usecases.order;


import ir.mehdi.mycleanarch.domain.models.Customer;
import ir.mehdi.mycleanarch.domain.models.Identity;
import ir.mehdi.mycleanarch.domain.models.Order;
import ir.mehdi.mycleanarch.domain.models.Product;
import ir.mehdi.mycleanarch.domain.repositories.OrderRepository;
import ir.mehdi.mycleanarch.infrastructure.entities.TestCoreEntityGenerator;
import ir.mehdi.mycleanarch.usecases.product.GetProductsByStoreAndProductsIdUseCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;


import static ir.mehdi.mycleanarch.infrastructure.entities.TestCoreEntityGenerator.randomId;
import static ir.mehdi.mycleanarch.infrastructure.entities.TestCoreEntityGenerator.randomQuantity;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
public class CreateOrderUseCaseTest {

    @InjectMocks
    private CreateOrderUseCase useCase;

    @Mock
    private GetProductsByStoreAndProductsIdUseCase getProductsByStoreAndProductsIdUseCase;

    @Mock
    private OrderRepository orderRepository;

    @Test
    public void executeShouldCreateAndReturnOrder() {
        // given
        Identity storeId = randomId();
        CreateOrderUseCase.InputItem useCaseInputItem =
                new CreateOrderUseCase.InputItem(randomId(), randomQuantity());

        GetProductsByStoreAndProductsIdUseCase.InputValues getProductsInput =
                new GetProductsByStoreAndProductsIdUseCase.InputValues(storeId, singletonList(useCaseInputItem.getId()));

        Product product = TestCoreEntityGenerator.randomProduct();
        product.setId(useCaseInputItem.getId());

        GetProductsByStoreAndProductsIdUseCase.OutputValues getProductsOutput =
                new GetProductsByStoreAndProductsIdUseCase.OutputValues(singletonList(product));

        Customer customer = TestCoreEntityGenerator.randomCustomer();

        CreateOrderUseCase.InputValues useCaseInput =
                new CreateOrderUseCase.InputValues(customer, storeId, singletonList(useCaseInputItem));

        Order expected = TestCoreEntityGenerator.randomOrder();

        // and
        doReturn(getProductsOutput)
                .when(getProductsByStoreAndProductsIdUseCase)
                .execute(eq(getProductsInput));

        // and
        doReturn(expected)
                .when(orderRepository)
                .persist(any(Order.class));

        // when
        Order actual = useCase.execute(useCaseInput).getOrder();

        // then
        assertThat(actual).isEqualTo(expected);
    }
}