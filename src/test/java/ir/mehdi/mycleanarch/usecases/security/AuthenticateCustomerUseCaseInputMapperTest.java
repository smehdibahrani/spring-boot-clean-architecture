package ir.mehdi.mycleanarch.usecases.security;

import ir.mehdi.mycleanarch.infrastructure.controllers.customer.SignInRequest;
import org.junit.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import static org.assertj.core.api.Assertions.assertThat;

public class AuthenticateCustomerUseCaseInputMapperTest {

    @Test
    public void mapReturnsUsernameAuthenticationToken() {
        // given
        final String email = "email";
        final String password = "password";
        SignInRequest signInRequest = new SignInRequest(email, password);

        // when
        UsernamePasswordAuthenticationToken actual =
                AuthenticateCustomerUseCaseInputMapper.map(signInRequest).getAuthenticationToken();

        // then
        assertThat(actual.getPrincipal()).isEqualTo(email);
        assertThat(actual.getCredentials()).isEqualTo(password);
    }
}