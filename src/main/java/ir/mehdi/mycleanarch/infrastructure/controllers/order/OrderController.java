package ir.mehdi.mycleanarch.infrastructure.controllers.order;

import ir.mehdi.mycleanarch.domain.models.Identity;
import ir.mehdi.mycleanarch.domain.usecases.UseCaseExecutor;
import ir.mehdi.mycleanarch.infrastructure.controllers.ApiResponse;
import ir.mehdi.mycleanarch.infrastructure.controllers.customer.CustomerResponse;
import ir.mehdi.mycleanarch.usecases.order.*;
import ir.mehdi.mycleanarch.usecases.security.CurrentUser;
import ir.mehdi.mycleanarch.usecases.security.UserPrincipal;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/order")
public class OrderController  {
    private final UseCaseExecutor useCaseExecutor;
    private final CreateOrderUseCase createOrderUseCase;
    private final GetOrderUseCase getOrderUseCase;
    private final GetCustomerOrderUseCase getCustomerOrderUseCase;
    private final DeleteOrderUseCase deleteOrderUseCase;
    private final PayOrderUseCase payOrderUseCase;
    private final DeliveryOrderUseCase deliveryOrderUseCase;

    public OrderController(UseCaseExecutor useCaseExecutor,
                           CreateOrderUseCase createOrderUseCase,
                           GetOrderUseCase getOrderUseCase,
                           GetCustomerOrderUseCase getCustomerOrderUseCase,
                           DeleteOrderUseCase deleteOrderUseCase,
                           PayOrderUseCase payOrderUseCase,
                           DeliveryOrderUseCase deliveryOrderUseCase) {
        this.useCaseExecutor = useCaseExecutor;
        this.createOrderUseCase = createOrderUseCase;
        this.getOrderUseCase = getOrderUseCase;
        this.getCustomerOrderUseCase = getCustomerOrderUseCase;
        this.deleteOrderUseCase = deleteOrderUseCase;
        this.payOrderUseCase = payOrderUseCase;
        this.deliveryOrderUseCase = deliveryOrderUseCase;
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public CompletableFuture<ResponseEntity<ApiResponse>> create(@CurrentUser UserPrincipal userDetails,
                                                                 HttpServletRequest httpServletRequest,
                                                                 @Valid @RequestBody OrderRequest orderRequest) {
        return useCaseExecutor.execute(
                createOrderUseCase,
                CreateOrderInputMapper.map(orderRequest, userDetails),
                (outputValues) -> CreateOrderOutputMapper.map(outputValues.getOrder(), httpServletRequest)
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public CompletableFuture<OrderResponse> getById(@PathVariable Long id) {
        return useCaseExecutor.execute(
                getOrderUseCase,
                new GetOrderUseCase.InputValues(new Identity(id)),
                (outputValues) -> OrderResponse.from(outputValues.getOrder())
        );
    }

    @GetMapping("/{id}/customer")
    @PreAuthorize("hasRole('USER')")
    public CompletableFuture<CustomerResponse> getCustomerById(@PathVariable Long id) {
        return useCaseExecutor.execute(
                getCustomerOrderUseCase,
                new GetCustomerOrderUseCase.InputValues(new Identity(id)),
                (outputValues) -> CustomerResponse.from(outputValues.getCustomer())
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public CompletableFuture<ApiResponse> delete(@PathVariable Long id) {
        return useCaseExecutor.execute(
                deleteOrderUseCase,
                new DeleteOrderUseCase.InputValues(new Identity(id)),
                (outputValues) -> new ApiResponse(true, "Order successfully canceled")
        );
    }

    @PostMapping("/{id}/payment")
    @PreAuthorize("hasRole('USER')")
    public CompletableFuture<ApiResponse> pay(@PathVariable Long id) {
        return useCaseExecutor.execute(
                payOrderUseCase,
                new DeleteOrderUseCase.InputValues(new Identity(id)),
                (outputValues) -> new ApiResponse(true, "Order successfully paid")
        );
    }

    @PostMapping("/{id}/delivery")
    @PreAuthorize("hasRole('USER')")
    public CompletableFuture<ApiResponse> delivery(@PathVariable Long id) {
        return useCaseExecutor.execute(
                deliveryOrderUseCase,
                new DeleteOrderUseCase.InputValues(new Identity(id)),
                (outputValues) -> new ApiResponse(true, "Order successfully delivered")
        );
    }
}
