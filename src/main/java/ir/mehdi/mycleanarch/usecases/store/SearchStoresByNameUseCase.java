package ir.mehdi.mycleanarch.usecases.store;


import ir.mehdi.mycleanarch.domain.models.Store;
import ir.mehdi.mycleanarch.domain.repositories.StoreRepository;
import ir.mehdi.mycleanarch.usecases.UseCase;
import lombok.Value;

import java.util.List;

public class SearchStoresByNameUseCase extends UseCase<SearchStoresByNameUseCase.InputValues, SearchStoresByNameUseCase.OutputValues> {
    private final StoreRepository repository;

    public SearchStoresByNameUseCase(StoreRepository repository) {
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
        private final List<Store> stores;
    }
}

