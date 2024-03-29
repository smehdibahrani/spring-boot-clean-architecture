package ir.mehdi.mycleanarch.infrastructure.repositories;

import ir.mehdi.mycleanarch.infrastructure.entities.CustomerEntity;
import ir.mehdi.mycleanarch.infrastructure.entities.TestCoreEntityGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringRunner;

import static ir.mehdi.mycleanarch.TestUtils.newInstanceWithProperties;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class JpaCustomerRepositoryTest {

    @Autowired
    private JpaCustomerRepository repository;

    @Autowired
    private TestEntityManager entityManager;

    @AutoConfigurationPackage
    @Configuration
    @EntityScan("ir.mehdi.mycleanarch.infrastructure.entities")
    static class Config {
    }

    @Test
    public void existsByEmailReturnsTrue() throws Exception {
        // given
        CustomerEntity customerData = newInstanceWithProperties(
                CustomerEntity.class, TestCoreEntityGenerator.randomCustomer(), "id");

        // and
        entityManager.persistAndFlush(customerData);

        // when
        boolean actual = repository.existsByEmail(customerData.getEmail());

        // then
        assertThat(actual).isTrue();
    }

    @Test
    public void existsByEmailReturnsFalse() throws Exception {
        // given
        CustomerEntity customerData = newInstanceWithProperties(
                CustomerEntity.class, TestCoreEntityGenerator.randomCustomer(), "id");

        // and
        entityManager.persistAndFlush(customerData);

        // when
        boolean actual = repository.existsByEmail("not found" + customerData.getEmail());

        // then
        assertThat(actual).isFalse();
    }
}