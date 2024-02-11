package ir.mehdi.mycleanarch.infrastructure.repositories;


import ir.mehdi.mycleanarch.infrastructure.entities.CousinEntity;
import ir.mehdi.mycleanarch.infrastructure.entities.ProductEntity;
import ir.mehdi.mycleanarch.infrastructure.entities.StoreEntity;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static ir.mehdi.mycleanarch.TestEntityGenerator.randomAddress;
import static ir.mehdi.mycleanarch.TestEntityGenerator.randomPrice;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class JpaStoreRepositoryTest {

    @Autowired
    private JpaStoreRepository repository;

    @Autowired
    private TestEntityManager entityManager;

    @Configuration
    @AutoConfigurationPackage
    @EntityScan("ir.mehdi.mycleanarch.infrastructure.entities")
    static class Config {
    }

    private CousinEntity cousineData;

    @Before
    public void setUp() throws Exception {
        cousineData = entityManager.persistFlushFind(CousinEntity.newInstance("name"));
    }

    @Test
    public void findByNameOrDescriptionContainingIgnoreCaseAllReturnsAllMatchStores() {
        // given
        Arrays.stream(new String[]{"aAbc", "abBc", "abCc"})
                .forEach(name -> {
                    final StoreEntity storeData = new StoreEntity(null, name, randomAddress(), cousineData, new HashSet<>());
                    entityManager.persistAndFlush(storeData);
                });

        // when
        List<StoreEntity> actual = repository.findByNameContainingIgnoreCase("abc");

        // then
        assertThat(actual).hasSize(2).extracting("name").containsOnly("aAbc", "abCc");
    }

    @Test
    public void findProductsByIdReturnsAllProducts() {
        // given
        StoreEntity storeData = entityManager.persistFlushFind(new StoreEntity(null, "name", randomAddress(), cousineData, new HashSet<>()));

        // and
        Arrays.stream(new String[]{"product A", "product B"})
                .forEach(name -> {
                    final ProductEntity productData = new ProductEntity(null, name, "desc", randomPrice(), storeData);
                    entityManager.persistAndFlush(productData);
                });

        // when
        List<ProductEntity> actual = repository.findProductsById(storeData.getId());

        // then
        assertThat(actual).hasSize(2).extracting("name").containsOnly("product A", "product B");
    }
}