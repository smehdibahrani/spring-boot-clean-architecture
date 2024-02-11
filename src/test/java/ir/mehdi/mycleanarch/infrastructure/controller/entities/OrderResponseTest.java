package ir.mehdi.mycleanarch.infrastructure.controller.entities;


import ir.mehdi.mycleanarch.domain.models.Order;
import ir.mehdi.mycleanarch.domain.models.OrderItem;
import ir.mehdi.mycleanarch.infrastructure.controllers.customer.CustomerResponse;
import ir.mehdi.mycleanarch.infrastructure.controllers.order.OrderResponse;
import ir.mehdi.mycleanarch.infrastructure.controllers.order.OrderResponseItem;
import ir.mehdi.mycleanarch.infrastructure.controllers.store.StoreResponse;
import ir.mehdi.mycleanarch.infrastructure.entities.TestCoreEntityGenerator;
import org.junit.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class OrderResponseTest {

    @Test
    public void fromOrder() {
        // given
        Order order = TestCoreEntityGenerator.randomOrder();
        OrderItem orderItem = order.getOrderItems().get(0);

        // when
        OrderResponse actual = OrderResponse.from(order);

        // then
        assertThat(actual.getId()).isEqualTo(order.getId().getNumber());
        assertThat(actual.getDate()).isEqualTo(order.getCreatedAt());
        assertThat(actual.getCustomer()).isEqualTo(CustomerResponse.from(order.getCustomer()));
        assertThat(actual.getContact()).isEqualTo(order.getCustomer().getName());
        assertThat(actual.getStore()).isEqualTo(StoreResponse.from(order.getStore()));
        assertThat(actual.getTotal()).isEqualTo(order.getTotal());
        assertThat(actual.getStatus()).isEqualTo(order.getStatus());
        assertThat(actual.getLastUpdate()).isEqualTo(order.getUpdatedAt());

        // and
        OrderResponseItem item = actual.getOrderItems().get(0);
        assertThat(item.getName()).isEqualTo(orderItem.getProduct().getName());
        assertThat(item.getPrice()).isEqualTo(orderItem.getProduct().getPrice());
        assertThat(item.getQuantity()).isEqualTo(orderItem.getQuantity());
        assertThat(item.getTotal()).isEqualTo(orderItem.getTotal());
    }
}