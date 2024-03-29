package ir.mehdi.mycleanarch.usecases.product;


import ir.mehdi.mycleanarch.domain.models.Product;
import ir.mehdi.mycleanarch.domain.repositories.ProductRepository;
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
public class GetAllProductsUseCaseTest {

    @InjectMocks
    private GetAllProductsUseCase useCase;

    @Mock
    private ProductRepository repository;

    @Test
    public void executeReturnsAllProducts() {
        // given
        Product product = TestCoreEntityGenerator.randomProduct();
        GetAllProductsUseCase.InputValues input =
                new GetAllProductsUseCase.InputValues();

        // and
        doReturn(Collections.singletonList(product))
                .when(repository)
                .getAll();

        // when
        List<Product> actual = useCase.execute(input).getProducts();

        // then
        assertThat(actual).containsOnly(product);
    }
}