package ir.mehdi.mycleanarch.usecases.store;


import ir.mehdi.mycleanarch.domain.models.Identity;
import ir.mehdi.mycleanarch.domain.models.Store;
import ir.mehdi.mycleanarch.domain.repositories.StoreRepository;
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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
public class GetStoreUseCaseTest {

    @InjectMocks
    private GetStoreUseCase useCase;

    @Mock
    private StoreRepository repository;

    @Test
    public void getStoreByIdentityReturnsStore() {
        // given
        Store store = TestCoreEntityGenerator.randomStore();
        GetStoreUseCase.InputValues input = new GetStoreUseCase.InputValues(store.getId());

        // and
        doReturn(Optional.of(store))
                .when(repository)
                .getById(eq(store.getId()));

        // when
        Store actual = useCase.execute(input).getStore();

        // then
        assertThat(actual).isEqualTo(store);
    }

    @Test
    public void getStoreByIdentityThrowsNotFound() {
        // given
        Identity id = TestCoreEntityGenerator.randomId();
        GetStoreUseCase.InputValues input = new GetStoreUseCase.InputValues(id);

        // and
        doReturn(Optional.empty())
                .when(repository)
                .getById(eq(id));

        // then
        assertThatThrownBy(() -> useCase.execute(input))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Store " + id.getNumber() + " not found");
    }
}