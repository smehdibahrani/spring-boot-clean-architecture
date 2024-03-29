package ir.mehdi.mycleanarch.usecases.store;


import ir.mehdi.mycleanarch.domain.models.Identity;
import ir.mehdi.mycleanarch.domain.models.Product;
import ir.mehdi.mycleanarch.domain.models.Store;
import ir.mehdi.mycleanarch.domain.repositories.StoreRepository;
import ir.mehdi.mycleanarch.infrastructure.entities.TestCoreEntityGenerator;
import ir.mehdi.mycleanarch.infrastructure.exceptions.NotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
public class GetProductsByStoreUseCaseTest {

    @InjectMocks
    private GetProductsByStoreUseCase useCase;

    @Mock
    private StoreRepository repository;

    @Test
    public void getProductsByStoreIdentityReturnsProductsWhenStoreFound() {
        // given
        Product product = TestCoreEntityGenerator.randomProduct();
        Store store = product.getStore();
        GetProductsByStoreUseCase.InputValues input =
                new GetProductsByStoreUseCase.InputValues(store.getId());

        // and
        doReturn(Collections.singletonList(product))
                .when(repository)
                .getProductsById(eq(store.getId()));

        // when
        List<Product> actual = useCase.execute(input).getProducts();

        // then
        assertThat(actual).containsOnly(product);
    }

    @Test
    public void getProductsByStoreIdentityThrowsNotFoundWhenStoreNotFound() {
        // given
        Identity id = TestCoreEntityGenerator.randomId();
        GetProductsByStoreUseCase.InputValues input = new GetProductsByStoreUseCase.InputValues(id);

        // and
        doReturn(Collections.emptyList())
                .when(repository)
                .getProductsById(eq(id));

        // then
        assertThatThrownBy(() -> useCase.execute(input))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("No store found by identity: " + id.getNumber());
    }
}