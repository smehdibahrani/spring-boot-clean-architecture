package ir.mehdi.mycleanarch.infrastructure.repositories;

import ir.mehdi.mycleanarch.domain.models.Customer;
import ir.mehdi.mycleanarch.infrastructure.entities.CustomerEntity;
import ir.mehdi.mycleanarch.infrastructure.entities.TestCoreEntityGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static ir.mehdi.mycleanarch.TestUtils.newInstanceWithProperties;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
public class CustomerRepositoryImplTest {

    @InjectMocks
    private CustomerRepositoryImpl customerRepository;

    @Mock
    private JpaCustomerRepository jpaCustomerRepository;

    @Test
    public void saveShouldPersistCustomerDataAndReturnsCustomer() throws Exception {
        // given
        Customer expected = TestCoreEntityGenerator.randomCustomer();
        Customer input = newInstanceWithProperties(Customer.class, expected, "id");

        // and
        doReturn(CustomerEntity.from(expected))
                .when(jpaCustomerRepository)
                .save(eq(CustomerEntity.from(input)));

        // when
        Customer actual = customerRepository.persist(input);

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void existsByEmailReturnsFalse() {
        //given
        String email = "email";

        // and
        doReturn(false)
                .when(jpaCustomerRepository)
                .existsByEmail(eq(email));

        // when
        boolean actual = customerRepository.existsByEmail(email);

        // then
        assertThat(actual).isFalse();
    }

    @Test
    public void existsByEmailReturnsTrue() {
        //given
        String email = "email";

        // and
        doReturn(true)
                .when(jpaCustomerRepository)
                .existsByEmail(eq(email));

        // when
        boolean actual = customerRepository.existsByEmail(email);

        // then
        assertThat(actual).isTrue();
    }
}