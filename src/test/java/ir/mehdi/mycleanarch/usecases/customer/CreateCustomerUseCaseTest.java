package ir.mehdi.mycleanarch.usecases.customer;


import ir.mehdi.mycleanarch.domain.models.Customer;
import ir.mehdi.mycleanarch.domain.repositories.CustomerRepository;
import ir.mehdi.mycleanarch.infrastructure.entities.TestCoreEntityGenerator;
import ir.mehdi.mycleanarch.infrastructure.exceptions.EmailAlreadyUsedException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
public class CreateCustomerUseCaseTest {

    @InjectMocks
    private CreateCustomerUseCase useCase;

    @Mock
    private CustomerRepository repository;

    @Test
    public void executeThrowsExceptionWhenEmailIsAlreadyRegistered() {
        // given
        CreateCustomerUseCase.InputValues input = new CreateCustomerUseCase.InputValues("name", "email@email.com", "address", "password");

        // and
        doReturn(true)
                .when(repository)
                .existsByEmail(input.getEmail());

        // then
        assertThatThrownBy(() -> useCase.execute(input))
                .isInstanceOf(EmailAlreadyUsedException.class)
                .hasMessage("Email address already in use!");
    }

    @Test
    public void executeReturnsCreatedCustomer() {
        // given
        Customer customer = TestCoreEntityGenerator.randomCustomer();
        Customer toBeMatched = Customer.newInstance(
                customer.getName(), customer.getEmail(),
                customer.getAddress(), customer.getPassword()
        );

        CreateCustomerUseCase.InputValues input = new CreateCustomerUseCase.InputValues(
                customer.getName(), customer.getEmail(),
                customer.getAddress(), customer.getPassword()
        );

        // and
        doReturn(customer)
                .when(repository)
                .persist(eq(toBeMatched));

        // when
        Customer actual = useCase.execute(input).getCustomer();

        // then
        assertThat(actual).isEqualTo(customer);
    }
}