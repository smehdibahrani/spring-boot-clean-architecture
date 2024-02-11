package ir.mehdi.mycleanarch.infrastructure.repositories;

import ir.mehdi.mycleanarch.infrastructure.entities.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;

import static ir.mehdi.mycleanarch.TestEntityGenerator.*;
import static ir.mehdi.mycleanarch.infrastructure.entities.TestCoreEntityGenerator.randomQuantity;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class JpaOrderRepositoryTest {

    @Configuration
    @EntityScan("ir.mehdi.mycleanarch.infrastructure.entities")
    @AutoConfigurationPackage
    static class Config {
    }

    @Autowired
    private JpaOrderRepository repository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void saveOrder() {
        // given
        CustomerEntity customerData = new CustomerEntity(null, randomName(), randomEmail(), randomAddress(), randomPassword());
        customerData = entityManager.persistFlushFind(customerData);

        //and
        CousinEntity cousineData = CousinEntity.newInstance(randomName());
        cousineData = entityManager.persistFlushFind(cousineData);

        // and
        StoreEntity storeData = new StoreEntity(null, randomName(), randomAddress(), cousineData, new HashSet<>());
        storeData = entityManager.persistFlushFind(storeData);

        // and
        ProductEntity productData = new ProductEntity(null, randomName(), randomDescription(), randomPrice(), storeData);
        productData = entityManager.persistAndFlush(productData);

        // and
        OrderItemEntity orderItemData = OrderItemEntity.newInstance(productData, randomQuantity());

        // and
        OrderEntity toBeSaved = OrderEntity.newInstance(
                customerData, storeData, new HashSet<>(singletonList(orderItemData))
        );

        // when
        OrderEntity savedOrder = repository.save(toBeSaved);

        // then
        assertThat(savedOrder.getId()).isNotNull();
    }
}