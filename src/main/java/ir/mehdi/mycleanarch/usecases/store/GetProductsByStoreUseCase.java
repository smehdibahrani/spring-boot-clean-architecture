package ir.mehdi.mycleanarch.usecases.store;


import ir.mehdi.mycleanarch.domain.models.Identity;
import ir.mehdi.mycleanarch.domain.models.Product;
import ir.mehdi.mycleanarch.domain.repositories.StoreRepository;
import ir.mehdi.mycleanarch.infrastructure.exceptions.NotFoundException;
import ir.mehdi.mycleanarch.usecases.UseCase;
import lombok.Value;

import java.util.List;

public class GetProductsByStoreUseCase extends UseCase<GetProductsByStoreUseCase.InputValues, GetProductsByStoreUseCase.OutputValues> {
    private final StoreRepository repository;

    public GetProductsByStoreUseCase(StoreRepository repository) {
        this.repository = repository;
    }

    @Override
    public OutputValues execute(InputValues inputValues) {
        Identity id = inputValues.getId();

        List<Product> products = repository.getProductsById(id);

        if (products.isEmpty()) {
            throw new NotFoundException("No store found by identity: " + id.getNumber());
        }

        return new OutputValues(products);
    }

    @Value
    public static class InputValues implements UseCase.InputValues {
        private final Identity id;
    }

    @Value
    public static class OutputValues implements UseCase.OutputValues {
        private final List<Product> products;
    }
}
