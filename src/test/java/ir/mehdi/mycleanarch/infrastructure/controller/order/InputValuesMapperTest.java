package ir.mehdi.mycleanarch.infrastructure.controller.order;


import ir.mehdi.mycleanarch.TestEntityGenerator;
import ir.mehdi.mycleanarch.domain.models.Customer;
import ir.mehdi.mycleanarch.domain.models.Identity;
import ir.mehdi.mycleanarch.infrastructure.controllers.order.CreateOrderInputMapper;
import ir.mehdi.mycleanarch.infrastructure.controllers.order.OrderRequest;
import ir.mehdi.mycleanarch.infrastructure.controllers.order.OrderRequestItem;
import ir.mehdi.mycleanarch.usecases.order.CreateOrderUseCase;
import ir.mehdi.mycleanarch.usecases.security.UserPrincipal;
import org.junit.Test;


import static ir.mehdi.mycleanarch.infrastructure.entities.TestCoreEntityGenerator.randomId;
import static ir.mehdi.mycleanarch.infrastructure.entities.TestCoreEntityGenerator.randomQuantity;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class InputValuesMapperTest {

    @Test
    public void mapReturnsCreateOrderInputMapper() {
        // given
        UserPrincipal userPrincipal = TestEntityGenerator.randomUserPrincipal();
        Customer customer = new Customer(
                new Identity(userPrincipal.getId()),
                userPrincipal.getUsername(),
                userPrincipal.getEmail(),
                userPrincipal.getAddress(),
                userPrincipal.getPassword()
        );

        Identity orderItemId = randomId();
        Integer orderItemQuantity = randomQuantity();
        OrderRequestItem orderItem = new OrderRequestItem(orderItemId.getNumber(), orderItemQuantity);

        Identity storeId = randomId();
        OrderRequest orderRequest = new OrderRequest(storeId.getNumber(), singletonList(orderItem));

        // when
        CreateOrderUseCase.InputValues actual = CreateOrderInputMapper.map(orderRequest, userPrincipal);

        // then
        assertThat(actual.getCustomer()).isEqualTo(customer);
//        assertThat(actual.getStoreId()).isEqualTo(storeId);
//        assertThat(actual.getOrderItems()).extracting("id").containsOnly(orderItemId);
//        assertThat(actual.getOrderItems()).extracting("quantity").containsOnly(orderItemQuantity);
    }
}