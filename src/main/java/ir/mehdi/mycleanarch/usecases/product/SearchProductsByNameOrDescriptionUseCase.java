package ir.mehdi.mycleanarch.usecases.product;


import ir.mehdi.mycleanarch.domain.models.Product;
import ir.mehdi.mycleanarch.domain.repositories.ProductRepository;
import ir.mehdi.mycleanarch.usecases.UseCase;
import lombok.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SearchProductsByNameOrDescriptionUseCase extends UseCase<SearchProductsByNameOrDescriptionUseCase.InputValues, SearchProductsByNameOrDescriptionUseCase.OutputValues> {
    private ProductRepository repository;

    public SearchProductsByNameOrDescriptionUseCase(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public OutputValues execute(InputValues input) {
        return new OutputValues(repository.searchByNameOrDescription(input.getSearchText()));
    }

    @Value
    public static class InputValues implements UseCase.InputValues {
        private final String searchText;
    }

    @Value
    public static class OutputValues implements UseCase.OutputValues {
        private final List<Product> products;
    }
}
