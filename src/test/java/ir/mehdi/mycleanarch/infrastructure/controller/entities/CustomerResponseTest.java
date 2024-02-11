package ir.mehdi.mycleanarch.infrastructure.controller.entities;


import ir.mehdi.mycleanarch.domain.models.Customer;
import ir.mehdi.mycleanarch.infrastructure.controllers.customer.CustomerResponse;
import ir.mehdi.mycleanarch.infrastructure.entities.TestCoreEntityGenerator;
import org.junit.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class CustomerResponseTest {

    @Test
    public void fromCustomer() {
        // given
        Customer customer = TestCoreEntityGenerator.randomCustomer();

        // when
        CustomerResponse actual = CustomerResponse.from(customer);

        // then
        assertThat(actual.getName()).isEqualTo(customer.getName());
        assertThat(actual.getEmail()).isEqualTo(customer.getEmail());
        assertThat(actual.getAddress()).isEqualTo(customer.getAddress());
    }
}