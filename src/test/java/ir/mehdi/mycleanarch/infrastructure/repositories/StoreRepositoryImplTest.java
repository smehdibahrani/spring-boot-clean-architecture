package ir.mehdi.mycleanarch.infrastructure.repositories;


import ir.mehdi.mycleanarch.domain.models.Identity;
import ir.mehdi.mycleanarch.domain.models.Product;
import ir.mehdi.mycleanarch.domain.models.Store;
import ir.mehdi.mycleanarch.infrastructure.entities.ProductEntity;
import ir.mehdi.mycleanarch.infrastructure.entities.StoreEntity;
import ir.mehdi.mycleanarch.infrastructure.entities.TestCoreEntityGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
public class StoreRepositoryImplTest {

    @InjectMocks
    private StoreRepositoryImpl storeRepository;

    @Mock
    private JpaStoreRepository jpaStoreRepository;

    @Test
    public void getAllReturnsAllStore() {
        // given
        Store store = TestCoreEntityGenerator.randomStore();
        StoreEntity storeData = StoreEntity.from(store);

        // and
        doReturn(Collections.singletonList(storeData))
                .when(jpaStoreRepository)
                .findAll();

        // when
        List<Store> actual = storeRepository.getAll();

        // then
        assertThat(actual).containsOnly(store);
    }

    @Test
    public void searchStoresByNameReturnsAllMatchStores() {
        // given
        String text = "abc";
        Store store = TestCoreEntityGenerator.randomStore();
        StoreEntity storeData = StoreEntity.from(store);

        // and
        doReturn(Collections.singletonList(storeData))
                .when(jpaStoreRepository)
                .findByNameContainingIgnoreCase(text);

        // when
        List<Store> actual = storeRepository.searchByName(text);

        // then
        assertThat(actual).containsOnly(store);
    }

    @Test
    public void getStoreByIdentityReturnsOptionalStore() {
        // given
        Store store = TestCoreEntityGenerator.randomStore();
        StoreEntity storeData = StoreEntity.from(store);

        // and
        doReturn(Optional.of(storeData))
                .when(jpaStoreRepository)
                .findById(store.getId().getNumber());

        // when
        Optional<Store> actual = storeRepository.getById(store.getId());

        // then
        assertThat(actual).isEqualTo(Optional.of(store));
    }

    @Test
    public void getStoreByIdentityReturnsOptionalEmpty() {
        // given
        Identity id = TestCoreEntityGenerator.randomId();

        // and
        doReturn(Optional.empty())
                .when(jpaStoreRepository)
                .findById(id.getNumber());

        // when
        Optional<Store> actual = storeRepository.getById(id);

        // then
        assertThat(actual).isEqualTo(Optional.empty());
    }

    @Test
    public void getProductsByIdentityReturnsProducts() {
        // given
        Product product = TestCoreEntityGenerator.randomProduct();
        ProductEntity productData = ProductEntity.from(product);
        Identity id = product.getStore().getId();

        // and
        doReturn(Collections.singletonList(productData))
                .when(jpaStoreRepository)
                .findProductsById(id.getNumber());

        //when
        List<Product> actual = storeRepository.getProductsById(id);

        // then
        assertThat(actual).containsOnly(product);
    }
}