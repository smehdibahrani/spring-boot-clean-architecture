package ir.mehdi.mycleanarch.infrastructure.controller.customer;


import ir.mehdi.mycleanarch.infrastructure.controllers.customer.SignUpRequest;
import ir.mehdi.mycleanarch.usecases.customer.CreateCustomerUseCase;
import ir.mehdi.mycleanarch.usecases.security.CreateCustomerInputMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
public class InputValuesMapperTest {

    @InjectMocks
    private CreateCustomerInputMapper inputMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    public void mapReturnsCreateCustomerInput() {
        // given
        SignUpRequest signUpRequest = new SignUpRequest("name", "email@email.com", "address", "password");
        CreateCustomerUseCase.InputValues inputValues = SignUpRequest.from(signUpRequest);

        // and
        doReturn("encrypt")
                .when(passwordEncoder)
                .encode(eq("password"));

        // when
        CreateCustomerUseCase.InputValues actual = inputMapper.map(signUpRequest);

        // then
        assertThat(actual).isEqualToIgnoringGivenFields(inputValues, "password");
        assertThat(actual.getPassword()).isEqualTo("encrypt");
    }
}