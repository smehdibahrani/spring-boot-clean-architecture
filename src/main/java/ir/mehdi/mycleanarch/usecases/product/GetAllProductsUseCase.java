package ir.mehdi.mycleanarch.usecases.product;


import ir.mehdi.mycleanarch.domain.models.Product;
import ir.mehdi.mycleanarch.domain.repositories.ProductRepository;
import ir.mehdi.mycleanarch.usecases.UseCase;
import lombok.Value;

import java.util.List;

public class GetAllProductsUseCase extends UseCase<GetAllProductsUseCase.InputValues, GetAllProductsUseCase.OutputValues> {
    private final ProductRepository repository;

    public GetAllProductsUseCase(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public OutputValues execute(InputValues input) {
        return new OutputValues(repository.getAll());
    }

    @Value
    public static class InputValues implements UseCase.InputValues {
    }

    @Value
    public static class OutputValues implements UseCase.OutputValues {
        private final List<Product> products;
    }
}
