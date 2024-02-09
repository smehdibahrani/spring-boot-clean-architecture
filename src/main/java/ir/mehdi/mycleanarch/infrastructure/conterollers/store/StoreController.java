package ir.mehdi.mycleanarch.infrastructure.conterollers.store;


import ir.mehdi.mycleanarch.domain.models.Identity;
import ir.mehdi.mycleanarch.domain.usecases.UseCaseExecutor;
import ir.mehdi.mycleanarch.infrastructure.conterollers.product.ProductResponse;
import ir.mehdi.mycleanarch.usecases.store.GetAllStoresUseCase;
import ir.mehdi.mycleanarch.usecases.store.GetProductsByStoreUseCase;
import ir.mehdi.mycleanarch.usecases.store.GetStoreUseCase;
import ir.mehdi.mycleanarch.usecases.store.SearchStoresByNameUseCase;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/store")
public class StoreController {

    private final UseCaseExecutor useCaseExecutor;
    private final GetAllStoresUseCase getAllStoresUseCase;
    private final SearchStoresByNameUseCase searchStoresByNameUseCase;
    private final GetStoreUseCase getStoreUseCase;
    private final GetProductsByStoreUseCase getProductsByStoreUseCase;

    public StoreController(UseCaseExecutor useCaseExecutor,
                           GetAllStoresUseCase getAllStoresUseCase,
                           SearchStoresByNameUseCase searchStoresByNameUseCase,
                           GetStoreUseCase getStoreUseCase,
                           GetProductsByStoreUseCase getProductsByStoreUseCase) {
        this.useCaseExecutor = useCaseExecutor;
        this.getAllStoresUseCase = getAllStoresUseCase;
        this.searchStoresByNameUseCase = searchStoresByNameUseCase;
        this.getStoreUseCase = getStoreUseCase;
        this.getProductsByStoreUseCase = getProductsByStoreUseCase;
    }

    @GetMapping
    public CompletableFuture<List<StoreResponse>> getAll() {
        return useCaseExecutor.execute(
                getAllStoresUseCase,
                new GetAllStoresUseCase.InputValues(),
                (outputValues) -> StoreResponse.from(outputValues.getStores()));
    }

    @GetMapping("/search/{text}")
    public CompletableFuture<List<StoreResponse>> getAllStoresByNameMatching(@PathVariable String text) {
        return useCaseExecutor.execute(
                searchStoresByNameUseCase,
                new SearchStoresByNameUseCase.InputValues(text),
                (outputValues) -> StoreResponse.from(outputValues.getStores()));
    }

    @GetMapping("/{id}")
    public CompletableFuture<StoreResponse> getStoreByIdentity(@PathVariable Long id) {
        return useCaseExecutor.execute(
                getStoreUseCase,
                new GetStoreUseCase.InputValues(new Identity(id)),
                (outputValues) -> StoreResponse.from(outputValues.getStore()));
    }

    @GetMapping("/{id}/products")
    public CompletableFuture<List<ProductResponse>> getProductsBy(@PathVariable Long id) {
        return useCaseExecutor.execute(
                getProductsByStoreUseCase,
                new GetProductsByStoreUseCase.InputValues(new Identity(id)),
                (outputValues) -> ProductResponse.from(outputValues.getProducts()));
    }
}
