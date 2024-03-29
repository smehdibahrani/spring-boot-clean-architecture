package ir.mehdi.mycleanarch.usecases.product;

import ir.mehdi.mycleanarch.domain.models.Identity;
import ir.mehdi.mycleanarch.domain.models.Product;
import ir.mehdi.mycleanarch.domain.repositories.ProductRepository;
import ir.mehdi.mycleanarch.infrastructure.entities.TestCoreEntityGenerator;
import ir.mehdi.mycleanarch.infrastructure.exceptions.NotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
public class GetProductUseCaseTest {

    @InjectMocks
    private GetProductUseCase useCase;

    @Mock
    private ProductRepository repository;

    @Test
    public void executeThrowsException() {
        // given
        Identity id = TestCoreEntityGenerator.randomId();
        GetProductUseCase.InputValues input =
                new GetProductUseCase.InputValues(id);

        // and
        doReturn(Optional.empty())
                .when(repository)
                .getById(id);

        // then
        assertThatThrownBy(() -> useCase.execute(input))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Product " + id.getNumber() + " not found");
    }

    @Test
    public void executeReturnsProduct() {
        // given
        Product product = TestCoreEntityGenerator.randomProduct();
        GetProductUseCase.InputValues input =
                new GetProductUseCase.InputValues(product.getId());

        // and
        doReturn(Optional.of(product))
                .when(repository)
                .getById(product.getId());

        // when
        Product actual = useCase.execute(input).getProduct();

        // then
        assertThat(actual).isEqualTo(product);
    }
}