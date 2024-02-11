package ir.mehdi.mycleanarch.infrastructure.repositories;


import ir.mehdi.mycleanarch.domain.models.Cousin;
import ir.mehdi.mycleanarch.domain.models.Identity;
import ir.mehdi.mycleanarch.domain.models.Store;
import ir.mehdi.mycleanarch.infrastructure.entities.CousinEntity;
import ir.mehdi.mycleanarch.infrastructure.entities.StoreEntity;
import ir.mehdi.mycleanarch.infrastructure.entities.TestCoreEntityGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
public class CousineRepositoryImplTest {

    @InjectMocks
    private CousinRepositoryImpl cousineRepository;

    @Mock
    private JpaCousinRepository jpaCousineRepository;

    @Test
    public void getStoresByIdentityReturnsStores() {
        // given
        Store store = TestCoreEntityGenerator.randomStore();
        Identity id = TestCoreEntityGenerator.randomId();

        StoreEntity storeData = StoreEntity.from(store);

        // and
        doReturn(Collections.singletonList(storeData))
                .when(jpaCousineRepository)
                .findStoresById(id.getNumber());

        // when
        final List<Store> actual = cousineRepository.getStoresById(id);

        // then
        assertThat(actual).containsOnly(store);
    }

    @Test
    public void getAllReturnsAllCousines() {
        // given
        Cousin cousine = TestCoreEntityGenerator.randomCousine();
        CousinEntity cousineData = CousinEntity.from(cousine);

        // and
        doReturn(Collections.singletonList(cousineData))
                .when(jpaCousineRepository)
                .findAll();
        // when
        final List<Cousin> actual = cousineRepository.getAll();

        // then
        assertThat(actual).containsOnly(cousine);
    }

    @Test
    public void searchCousineByNameReturnsAllCousines() {
        // given
        Cousin cousine = TestCoreEntityGenerator.randomCousine();
        CousinEntity cousineData = CousinEntity.from(cousine);
        String search = "abc";

        // and
        doReturn(Collections.singletonList(cousineData))
                .when(jpaCousineRepository)
                .findByNameContainingIgnoreCase(search);

        // when
        final List<Cousin> actual = cousineRepository.searchByName(search);

        // then
        assertThat(actual).isEqualTo(Collections.singletonList(cousine));
    }
}