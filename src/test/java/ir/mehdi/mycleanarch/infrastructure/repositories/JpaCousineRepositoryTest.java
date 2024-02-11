package ir.mehdi.mycleanarch.infrastructure.repositories;


import ir.mehdi.mycleanarch.infrastructure.entities.CousinEntity;
import ir.mehdi.mycleanarch.infrastructure.entities.StoreEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static ir.mehdi.mycleanarch.TestEntityGenerator.randomAddress;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class JpaCousineRepositoryTest {

    @Autowired
    private JpaCousinRepository repository;

    @Autowired
    private TestEntityManager entityManager;

    @Configuration
    @AutoConfigurationPackage
    @EntityScan("ir.mehdi.mycleanarch.infrastructure.entities")
    static class Config {
    }

    @Test
    public void findByNameIgnoreCase() {
        // given
        Arrays.stream(new String[]{"aAbc", "abBc", "abCc"})
                .forEach(name -> entityManager.persistAndFlush(CousinEntity.newInstance(name)));

        // when
        final List<CousinEntity> actual = repository.findByNameContainingIgnoreCase("abc");

        // then
        assertThat(actual)
                .extracting("name")
                .containsOnly("aAbc", "abCc");
    }

    @Test
    public void getStoresByCousineId() {
        // given
        CousinEntity cousine = entityManager.persistFlushFind(CousinEntity.newInstance("name"));
        StoreEntity storeA = entityManager.persistFlushFind(new StoreEntity(null, "name A", randomAddress(), cousine, new HashSet<>()));
        StoreEntity storeB = entityManager.persistFlushFind(new StoreEntity(null, "name B", randomAddress(), cousine, new HashSet<>()));

        // and
        cousine.addStore(storeA);
        cousine.addStore(storeB);
        entityManager.persistAndFlush(cousine);

        // when
        List<StoreEntity> actual = new ArrayList<>(repository.findStoresById(cousine.getId()));

        // then
        assertThat(actual).hasSize(2);
        assertThat(actual.get(0)).isEqualToComparingOnlyGivenFields(storeA, "id", "name", "address");
        assertThat(actual.get(1)).isEqualToComparingOnlyGivenFields(storeB, "id", "name", "address");
    }

    @Test
    public void getStoresByCousineIdReturnsEmpty() {
        List<StoreEntity> actual = repository.findStoresById(0L);

        assertThat(actual).hasSize(0);
    }
}