package ir.mehdi.mycleanarch.infrastructure.controller.order;


import ir.mehdi.mycleanarch.domain.models.Order;
import ir.mehdi.mycleanarch.infrastructure.controllers.ApiResponse;
import ir.mehdi.mycleanarch.infrastructure.controllers.order.CreateOrderOutputMapper;
import ir.mehdi.mycleanarch.infrastructure.entities.TestCoreEntityGenerator;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;

import javax.servlet.http.HttpServletRequest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


public class CreateOrderOutputMapperTest {

    @Test
    public void mapOrderToResponseCreated() {
        // given
        Order order = TestCoreEntityGenerator.randomOrder();
        HttpServletRequest httpServletRequest = new MockHttpServletRequest("", "");

        // when
        ResponseEntity<ApiResponse> actual = CreateOrderOutputMapper.map(order, httpServletRequest);

        // then
        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(actual.getBody().getSuccess()).isTrue();
        assertThat(actual.getBody().getMessage()).isEqualTo("order created successfully");
        assertThat(actual.getHeaders().getLocation().toString()).isEqualTo("http://localhost/order/" + order.getId().getNumber());
    }
}