package ir.mehdi.mycleanarch.infrastructure.controllers.order;


import ir.mehdi.mycleanarch.domain.models.Order;
import ir.mehdi.mycleanarch.infrastructure.controllers.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;

public final class CreateOrderOutputMapper {

    public static ResponseEntity<ApiResponse> map(Order order, HttpServletRequest httpServletRequest) {
        URI location = ServletUriComponentsBuilder
                .fromContextPath(httpServletRequest)
                .path("/order/{id}")
                .buildAndExpand(order.getId().getNumber())
                .toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true, "order created successfully"));
    }
}
