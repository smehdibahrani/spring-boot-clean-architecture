package ir.mehdi.mycleanarch.usecases.security;

import ir.mehdi.mycleanarch.infrastructure.conterollers.customer.SignUpRequest;
import ir.mehdi.mycleanarch.usecases.customer.CreateCustomerUseCase;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CreateCustomerInputMapper {
    private final PasswordEncoder passwordEncoder;

    public CreateCustomerInputMapper(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public CreateCustomerUseCase.InputValues map(SignUpRequest signUpRequest) {
        return new CreateCustomerUseCase.InputValues(
                signUpRequest.getName(),
                signUpRequest.getEmail(),
                signUpRequest.getAddress(),
                passwordEncoder.encode(signUpRequest.getPassword()));
    }
}
