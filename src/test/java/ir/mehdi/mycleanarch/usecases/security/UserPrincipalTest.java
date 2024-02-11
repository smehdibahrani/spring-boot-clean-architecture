package ir.mehdi.mycleanarch.usecases.security;

import ir.mehdi.mycleanarch.infrastructure.entities.CustomerEntity;
import org.junit.Test;

import static ir.mehdi.mycleanarch.infrastructure.entities.TestCoreEntityGenerator.randomCustomer;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class UserPrincipalTest {

    @Test
    public void fromCustomer() {
        // given
        CustomerEntity customerData = CustomerEntity.from(randomCustomer());

        // when
        UserPrincipal actual = UserPrincipal.from(customerData);

        // then
        assertThat(actual).isEqualToComparingOnlyGivenFields(customerData, "name", "id", "password", "address");
        //assertThat(actual.getAuthorities()).extracting("role").containsOnly("ROLE_USER");
    }
}