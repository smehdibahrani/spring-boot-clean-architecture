package ir.mehdi.mycleanarch.infrastructure.conterollers.product;

import ir.mehdi.mycleanarch.domain.models.Identity;
import ir.mehdi.mycleanarch.domain.usecases.UseCaseExecutor;
import ir.mehdi.mycleanarch.usecases.product.GetAllProductsUseCase;
import ir.mehdi.mycleanarch.usecases.product.GetProductUseCase;
import ir.mehdi.mycleanarch.usecases.product.SearchProductsByNameOrDescriptionUseCase;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/product")
public class ProductController {
    private final UseCaseExecutor useCaseExecutor;
    private final GetAllProductsUseCase getAllProductsUseCase;
    private final GetProductUseCase getProductUseCase;
    private final SearchProductsByNameOrDescriptionUseCase searchProductsByNameOrDescriptionUseCase;

    public ProductController(UseCaseExecutor useCaseExecutor,
                             GetAllProductsUseCase getAllProductsUseCase,
                             GetProductUseCase getProductUseCase,
                             SearchProductsByNameOrDescriptionUseCase searchProductsByNameOrDescriptionUseCase) {
        this.useCaseExecutor = useCaseExecutor;
        this.getAllProductsUseCase = getAllProductsUseCase;
        this.getProductUseCase = getProductUseCase;
        this.searchProductsByNameOrDescriptionUseCase = searchProductsByNameOrDescriptionUseCase;
    }

    @GetMapping
    public CompletableFuture<List<ProductResponse>> getAllProducts() {
        return useCaseExecutor.execute(
                getAllProductsUseCase,
                new GetAllProductsUseCase.InputValues(),
                (outputValues) -> ProductResponse.from(outputValues.getProducts()));
    }

    @GetMapping("/{id}")
    public CompletableFuture<ProductResponse> getByIdentity(@PathVariable Long id) {
        return useCaseExecutor.execute(
                getProductUseCase,
                new GetProductUseCase.InputValues(new Identity(id)),
                (outputValues) -> ProductResponse.from(outputValues.getProduct()));
    }

    @GetMapping("/search/{text}")
    public CompletableFuture<List<ProductResponse>> getByMatchingName(@PathVariable String text) {
        return useCaseExecutor.execute(
                searchProductsByNameOrDescriptionUseCase,
                new SearchProductsByNameOrDescriptionUseCase.InputValues(text),
                (outputValues) -> ProductResponse.from(outputValues.getProducts()));
    }
}
