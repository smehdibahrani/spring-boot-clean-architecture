package ir.mehdi.mycleanarch.usecases.security;

import ir.mehdi.mycleanarch.usecases.UseCase;
import lombok.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthenticateCustomerUseCase extends UseCase<AuthenticateCustomerUseCase.InputValues, AuthenticateCustomerUseCase.OutputValues> {
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    public AuthenticateCustomerUseCase(AuthenticationManager authenticationManager,
                                       JwtProvider jwtProvider) {
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
    }

    @Override
    public OutputValues execute(InputValues input) {
        Authentication authentication = authenticationManager.authenticate(input.getAuthenticationToken());
        Authentication authentication2 = authenticationManager.authenticate(input.getAuthenticationToken());
        System.out.println(authentication2.isAuthenticated());
        return new OutputValues(jwtProvider.generateToken(authentication2));
    }

    @Value
    public static class InputValues implements UseCase.InputValues {
        private final UsernamePasswordAuthenticationToken authenticationToken;
    }

    @Value
    public static class OutputValues implements UseCase.OutputValues {
        private final String jwtToken;
    }
}
