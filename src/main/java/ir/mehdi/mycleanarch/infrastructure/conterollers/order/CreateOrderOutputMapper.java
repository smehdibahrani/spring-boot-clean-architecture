package ir.mehdi.mycleanarch.infrastructure.conterollers.order;


import ir.mehdi.mycleanarch.domain.models.Order;
import ir.mehdi.mycleanarch.infrastructure.conterollers.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;

public final class CreateOrderOutputMapper {

    public static ResponseEntity<ApiResponse> map(Order order, HttpServletRequest httpServletRequest) {
        URI location = ServletUriComponentsBuilder
                .fromContextPath(httpServletRequest)
                .path("/Order/{id}")
                .buildAndExpand(order.getId().getNumber())
                .toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true, "order created successfully"));
    }
}
