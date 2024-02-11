package ir.mehdi.mycleanarch.usecases.security;

import ir.mehdi.mycleanarch.infrastructure.controllers.customer.SignInRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public final class AuthenticateCustomerUseCaseInputMapper {
    public static AuthenticateCustomerUseCase.InputValues map(SignInRequest signInRequest) {
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                signInRequest.getEmail(),
                signInRequest.getPassword());

        return new AuthenticateCustomerUseCase.InputValues(auth);
    }
}
