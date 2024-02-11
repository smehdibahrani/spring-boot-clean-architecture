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

import static ir.mehdi.mycleanarch.TestEntityGenerator.*;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class JpaProductRepositoryTest {

    @Autowired
    private JpaProductRepository repository;

    @Autowired
    private TestEntityManager entityManager;

    @AutoConfigurationPackage
    @Configuration
    @EntityScan("ir.mehdi.mycleanarch.infrastructure.entities")
    static class Config {
    }

    @Before
    public void setUp() {
        repository.deleteAll();
    }

    @Test
    public void findByStoreIdAndIdIsInReturnListOfProductData() {
        // given
        CousinEntity cousineData = entityManager.persistFlushFind(CousinEntity.newInstance("name"));
        StoreEntity storeData = entityManager.persistFlushFind(new StoreEntity(null,"name", randomAddress(), cousineData, new HashSet<>()));
        ProductEntity productData = entityManager.persistAndFlush(new ProductEntity(null, "name", "description", randomPrice(), storeData));

        // when
        List<ProductEntity> actual =
                repository.findByStoreIdAndIdIsIn(storeData.getId(), singletonList(productData.getId()));

        // then
        assertThat(actual).extracting("id").containsOnly(productData.getId());
    }

    @Test
    public void findByNameContainingIgnoreCase() {
        // given
        CousinEntity cousineData = entityManager.persistFlushFind(CousinEntity.newInstance(randomName()));
        StoreEntity storeData = entityManager.persistFlushFind(new StoreEntity(null, randomName(), randomAddress(), cousineData, new HashSet<>()));

        Arrays.stream(new String[]{"AABC", "ABBC", "ABCC"})
                .forEach(name -> {
                    String description = name;

                    if ("ABBC".equals(name)) {
                        description = "DESCRIPTION";
                    }

                    entityManager.persistAndFlush(new ProductEntity(null, name, description, randomPrice(), storeData));
                });

        // when
        List<ProductEntity> actual = repository.findByNameContainingOrDescriptionContainingAllIgnoreCase("abc", "des");

        // then
        assertThat(actual).hasSize(3).extracting("name").containsOnly("AABC", "ABBC", "ABCC");
    }
}