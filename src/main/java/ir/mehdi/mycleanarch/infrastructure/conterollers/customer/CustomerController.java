package ir.mehdi.mycleanarch.infrastructure.conterollers.customer;

import ir.mehdi.mycleanarch.domain.usecases.UseCaseExecutor;
import ir.mehdi.mycleanarch.infrastructure.conterollers.ApiResponse;
import ir.mehdi.mycleanarch.usecases.customer.CreateCustomerUseCase;
import ir.mehdi.mycleanarch.usecases.security.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("customer")
public class CustomerController {
    private final UseCaseExecutor useCaseExecutor;
    private final CreateCustomerUseCase createCustomerUseCase;
    private final CreateCustomerInputMapper createCustomerUseCaseInputMapper;
    private final AuthenticateCustomerUseCase authenticateCustomerUseCase;

    public CustomerController(UseCaseExecutor useCaseExecutor,
                              CreateCustomerUseCase createCustomerUseCase,
                              CreateCustomerInputMapper createCustomerUseCaseInputMapper,
                              AuthenticateCustomerUseCase authenticateCustomerUseCase) {
        this.useCaseExecutor = useCaseExecutor;
        this.createCustomerUseCase = createCustomerUseCase;
        this.createCustomerUseCaseInputMapper = createCustomerUseCaseInputMapper;
        this.authenticateCustomerUseCase = authenticateCustomerUseCase;
    }

    @PostMapping
    public CompletableFuture<ResponseEntity<ApiResponse>> signUp(@Valid @RequestBody SignUpRequest signUpRequest,
                                                                 HttpServletRequest httpServletRequest) {
        return useCaseExecutor.execute(
                createCustomerUseCase,
                createCustomerUseCaseInputMapper.map(signUpRequest),
                (outputValues) -> CreateCustomerUseCaseOutputMapper.map(outputValues.getCustomer(), httpServletRequest));
    }

    @PostMapping("/auth")
    public CompletableFuture<ResponseEntity<AuthenticationResponse>> signIn(@Valid @RequestBody SignInRequest signInRequest) {
        return useCaseExecutor.execute(
                authenticateCustomerUseCase,
                AuthenticateCustomerUseCaseInputMapper.map(signInRequest),
                AuthenticateCustomerUseCaseOutputMapper::map);
    }
}
