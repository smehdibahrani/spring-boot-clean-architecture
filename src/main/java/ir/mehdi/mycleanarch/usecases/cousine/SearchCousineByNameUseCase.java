package ir.mehdi.mycleanarch.usecases.cousine;


import ir.mehdi.mycleanarch.domain.models.Cousin;
import ir.mehdi.mycleanarch.domain.repositories.CousinRepository;
import ir.mehdi.mycleanarch.usecases.UseCase;
import lombok.Value;

import java.util.List;

public class SearchCousineByNameUseCase extends UseCase<SearchCousineByNameUseCase.InputValues, SearchCousineByNameUseCase.OutputValues> {

    private final CousinRepository repository;

    public SearchCousineByNameUseCase(CousinRepository repository) {
        this.repository = repository;
    }

    @Override
    public OutputValues execute(InputValues input) {
        return new OutputValues(repository.searchByName(input.getSearchText()));
    }

    @Value
    public static class InputValues implements UseCase.InputValues {
        private final String searchText;
    }

    @Value
    public static class OutputValues implements UseCase.OutputValues {
        private final List<Cousin> cousins;
    }
}
