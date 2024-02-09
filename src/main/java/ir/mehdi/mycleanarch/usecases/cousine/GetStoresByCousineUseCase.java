package ir.mehdi.mycleanarch.usecases.cousine;


import ir.mehdi.mycleanarch.domain.models.Identity;
import ir.mehdi.mycleanarch.domain.models.Store;
import ir.mehdi.mycleanarch.domain.repositories.CousinRepository;
import ir.mehdi.mycleanarch.infrastructure.exceptions.NotFoundException;
import ir.mehdi.mycleanarch.usecases.UseCase;
import lombok.Value;

import java.util.List;

public class GetStoresByCousineUseCase extends UseCase<GetStoresByCousineUseCase.InputValues, GetStoresByCousineUseCase.OutputValues> {
    private final CousinRepository repository;

    public GetStoresByCousineUseCase(CousinRepository repository) {
        this.repository = repository;
    }

    @Override
    public OutputValues execute(InputValues input) {
        Identity id = input.getId();

        List<Store> stores = repository.getStoresById(id);

        if (stores.isEmpty()) {
            throw new NotFoundException("Cousin %s not found", id.getNumber());
        }

        return new OutputValues(stores);
    }

    @Value
    public static class InputValues implements UseCase.InputValues {
        private final Identity id;
    }

    @Value
    public static class OutputValues implements UseCase.OutputValues {
        private final List<Store> stores;
    }
}
