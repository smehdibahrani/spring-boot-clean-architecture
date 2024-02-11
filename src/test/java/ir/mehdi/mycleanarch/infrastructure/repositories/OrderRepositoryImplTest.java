package ir.mehdi.mycleanarch.infrastructure.repositories;


import ir.mehdi.mycleanarch.TestUtils;
import ir.mehdi.mycleanarch.domain.models.Order;
import ir.mehdi.mycleanarch.infrastructure.entities.OrderEntity;
import ir.mehdi.mycleanarch.infrastructure.entities.TestCoreEntityGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
public class OrderRepositoryImplTest {

    @InjectMocks
    private OrderRepositoryImpl orderRepository;

    @Mock
    private JpaOrderRepository jpaRepository;

    @Test
    public void getByIdReturnOrderData() {
        // given
        Order expected = TestCoreEntityGenerator.randomOrder();
        OrderEntity toBeReturned = OrderEntity.from(expected);

        // and
        doReturn(Optional.of(toBeReturned))
                .when(jpaRepository)
                .findById(eq(expected.getId().getNumber()));

        // when
        Optional<Order> actual = orderRepository.getById(expected.getId());

        // then
        assertThat(actual.isPresent()).isTrue();
        assertThat(actual.get()).isEqualTo(expected);
    }

    @Test
    public void persistSaveAndReturnOrderData() throws Exception {
        // given
        Order expected = TestCoreEntityGenerator.randomOrder();
        OrderEntity toBeReturned = OrderEntity.from(expected);
        OrderEntity toBeMatched = TestUtils.newInstanceWithProperties(OrderEntity.class, toBeReturned);

        // and
        doReturn(toBeReturned)
                .when(jpaRepository)
                .save(eq(toBeMatched));

        // when
        Order actual = orderRepository.persist(expected);

        // then
        assertThat(actual).isEqualTo(expected);
    }
}