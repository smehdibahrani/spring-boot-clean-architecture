package ir.mehdi.mycleanarch.infrastructure.controllers.order;

import lombok.Value;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Value
public class OrderRequestItem {
    @NotNull
    private final Long id;

    @Min(1)
    @NotNull
    private final Integer quantity;
}
