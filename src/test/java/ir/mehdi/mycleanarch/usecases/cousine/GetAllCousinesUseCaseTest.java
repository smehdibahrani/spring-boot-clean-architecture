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
public class GetAllCousinesUseCaseTest {

    @InjectMocks
    private GetAllCousinsUseCase useCase;

    @Mock
    private CousinRepository repository;

    @Test
    public void returnsAllCousines() {
        // given
        List<Cousin> cousines = TestCoreEntityGenerator.randomCousines();
        GetAllCousinsUseCase.InputValues input =
                new GetAllCousinsUseCase.InputValues();

        // and
        doReturn(cousines)
                .when(repository)
                .getAll();

        // when
        final List<Cousin> actual = useCase.execute(input).getCousins();

        // then
        assertThat(actual).isEqualTo(cousines);
    }
}