package ir.mehdi.mycleanarch.usecases.cousine;


import ir.mehdi.mycleanarch.domain.models.Identity;
import ir.mehdi.mycleanarch.domain.models.Store;
import ir.mehdi.mycleanarch.domain.repositories.CousinRepository;
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
import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
public class GetStoresByCousineUseCaseTest {

    @InjectMocks
    private GetStoresByCousineUseCase useCase;

    @Mock
    private CousinRepository repository;

    @Test
    public void returnsStoresWhenCousineIdIsFound() {
        // given
        Store store = TestCoreEntityGenerator.randomStore();
        Identity id = TestCoreEntityGenerator.randomId();
        GetStoresByCousineUseCase.InputValues input = new GetStoresByCousineUseCase.InputValues(id);

        // and
        doReturn(Collections.singletonList(store))
                .when(repository)
                .getStoresById(id);

        // when
        final List<Store> actual = useCase.execute(input).getStores();

        // then
        assertThat(actual).containsOnly(store);
    }

    @Test
    public void throwsExceptionWhenCousineIdIsNotFound() {
        // given
        Identity id = TestCoreEntityGenerator.randomId();
        GetStoresByCousineUseCase.InputValues input = new GetStoresByCousineUseCase.InputValues(id);

        // and
        doReturn(Collections.emptyList())
                .when(repository)
                .getStoresById(id);

        // then
        assertThatThrownBy(() -> useCase.execute(input))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Cousin " + id.getNumber() + " not found");
    }
}