package ir.mehdi.mycleanarch.usecases.cousine;

import ir.mehdi.mycleanarch.domain.models.Cousin;
import ir.mehdi.mycleanarch.domain.repositories.CousinRepository;
import ir.mehdi.mycleanarch.infrastructure.entities.TestCoreEntityGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
public class SearchCousineByNameUseCaseTest {

    @Mock
    private CousinRepository repository;

    @InjectMocks
    private SearchCousineByNameUseCase useCase;

    @Test
    public void searchCousineByName() {
        // given
        List<Cousin> cousines = TestCoreEntityGenerator.randomCousines();
        String searchText = "abc";
        SearchCousineByNameUseCase.InputValues input =
                new SearchCousineByNameUseCase.InputValues(searchText);

        // and
        doReturn(cousines)
                .when(repository)
                .searchByName(searchText);

        // when
        final List<Cousin> actual = useCase.execute(input).getCousins();

        // then
        assertThat(actual).isEqualTo(cousines);
    }
}