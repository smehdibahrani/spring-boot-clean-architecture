package ir.mehdi.mycleanarch.infrastructure.controller.customer;

import com.fasterxml.jackson.databind.ObjectMapper;
import ir.mehdi.mycleanarch.domain.models.Customer;
import ir.mehdi.mycleanarch.infrastructure.controller.common.BaseControllerTest;
import ir.mehdi.mycleanarch.infrastructure.controllers.customer.CustomerController;
import ir.mehdi.mycleanarch.infrastructure.controllers.customer.SignInRequest;
import ir.mehdi.mycleanarch.infrastructure.controllers.customer.SignUpRequest;
import ir.mehdi.mycleanarch.infrastructure.entities.TestCoreEntityGenerator;
import ir.mehdi.mycleanarch.infrastructure.exceptions.EmailAlreadyUsedException;
import ir.mehdi.mycleanarch.usecases.UseCaseExecutorImpl;
import ir.mehdi.mycleanarch.usecases.customer.CreateCustomerUseCase;
import ir.mehdi.mycleanarch.usecases.security.AuthenticateCustomerUseCase;
import ir.mehdi.mycleanarch.usecases.security.AuthenticateCustomerUseCaseInputMapper;
import ir.mehdi.mycleanarch.usecases.security.CreateCustomerInputMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(value = CustomerController.class, secure = false)
public class CustomerControllerTest extends BaseControllerTest {

    @Configuration
    @ComponentScan(basePackages = {
            "ir.mehdi.mycleanarch.infrastructure.controllers.customer",
            "ir.mehdi.mycleanarch.infrastructure.exceptions"
    })
    static class Config {
    }

    private JacksonTester<SignUpRequest> signUpJson;
    private JacksonTester<SignInRequest> signInJson;

    @MockBean
    private AuthenticateCustomerUseCase authenticateCustomerUseCase;

    @MockBean
    private CreateCustomerUseCase createCustomerUseCase;

    @MockBean
    private CreateCustomerInputMapper createCustomerInputMapper;

    @SpyBean
    private UseCaseExecutorImpl useCaseExecutor;

    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setUp() {
        JacksonTester.initFields(this, new ObjectMapper());
    }

    @Override
    protected MockMvc getMockMvc() {
        return mockMvc;
    }

    @Test
    public void signInReturnsOkWhenAuthenticationWorks() throws Exception {
        // given
        SignInRequest signInRequest = new SignInRequest("email@email.com", "password");
        AuthenticateCustomerUseCase.InputValues input =
                AuthenticateCustomerUseCaseInputMapper.map(signInRequest);

        String token = "token";
        AuthenticateCustomerUseCase.OutputValues output = new AuthenticateCustomerUseCase.OutputValues(token);

        String payload = signInJson.write(signInRequest).getJson();

        // and
        doReturn(output)
                .when(authenticateCustomerUseCase)
                .execute(eq(input));

        // when
        RequestBuilder request = asyncPostRequest("/customer/auth", payload);

        // then
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.token", is(token)));
    }

    @Test
    public void signInReturnsBadRequestWhenAuthenticationManagerFails() throws Exception {
        // given
        SignInRequest signInRequest = new SignInRequest("email@email.com", "password");
        String payload = signInJson.write(signInRequest).getJson();
        AuthenticateCustomerUseCase.InputValues input =
                AuthenticateCustomerUseCaseInputMapper.map(signInRequest);

        // and
        doThrow(new UsernameNotFoundException("Error"))
                .when(authenticateCustomerUseCase)
                .execute(eq(input));

        // when
        RequestBuilder request = asyncPostRequest("/customer/auth", payload);
                // then
        mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.success", is(false)))
                .andExpect(jsonPath("$.message", is("Error")));
    }

    @Test
    public void signUpReturnsBadRequestWhenEmailIsAlreadyBeenUsed() throws Exception {
        // given
        final SignUpRequest signUpRequest = new SignUpRequest("name", "email@email.com", "address", "password");
        String payload = signUpJson.write(signUpRequest).getJson();
        CreateCustomerUseCase.InputValues inputValues = new CreateCustomerUseCase.InputValues(null, null, null, null);

        // and
        doReturn(inputValues)
                .when(createCustomerInputMapper)
                .map(eq(signUpRequest));

        // and
        doThrow(new EmailAlreadyUsedException("Error"))
                .when(createCustomerUseCase)
                .execute(inputValues);
        // when
        RequestBuilder request = asyncPostRequest("/customer", payload);

        // then
        mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.success", is(false)))
                .andExpect(jsonPath("$.message", is("Error")));
    }

    @Test
    public void signUpReturnsCreatedWhenIsANewCustomer() throws Exception {
        // given
        final SignUpRequest signUpRequest = new SignUpRequest("name", "email@email.com", "address", "password");
        String payload = signUpJson.write(signUpRequest).getJson();
        Customer customer = TestCoreEntityGenerator.randomCustomer();

        CreateCustomerUseCase.InputValues input = new CreateCustomerUseCase.InputValues(
                customer.getName(), customer.getEmail(),
                customer.getAddress(), customer.getPassword());

        CreateCustomerUseCase.OutputValues output = new CreateCustomerUseCase.OutputValues(customer);

        // and
        doReturn(input)
                .when(createCustomerInputMapper)
                .map(eq(signUpRequest));

        // and
        doReturn(output)
                .when(createCustomerUseCase)
                .execute(eq(input));

        // when
        RequestBuilder request = asyncPostRequest("/customer", payload);

        // then
        mockMvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(header().string("location", "http://localhost/customer/" + customer.getId().getNumber()))
                .andExpect(jsonPath("$.message", is("registered successfully")));
    }
}