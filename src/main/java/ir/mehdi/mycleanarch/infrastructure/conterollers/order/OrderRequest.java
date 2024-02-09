package ir.mehdi.mycleanarch.infrastructure.conterollers.order;

import lombok.Value;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Value
public class OrderRequest {
    @NotNull
    private final Long storeId;

    @NotEmpty
    private final List<OrderRequestItem> orderItems;
}
