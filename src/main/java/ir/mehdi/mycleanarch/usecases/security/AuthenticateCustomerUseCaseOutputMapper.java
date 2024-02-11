package ir.mehdi.mycleanarch.usecases.security;

import ir.mehdi.mycleanarch.infrastructure.controllers.customer.AuthenticationResponse;
import org.springframework.http.ResponseEntity;

public final class AuthenticateCustomerUseCaseOutputMapper {
    public static ResponseEntity<AuthenticationResponse> map(AuthenticateCustomerUseCase.OutputValues outputValues) {
        return ResponseEntity.ok(new AuthenticationResponse(outputValues.getJwtToken()));
    }
}
