package ir.mehdi.mycleanarch.infrastructure.controllers.cousine;

import ir.mehdi.mycleanarch.domain.models.Identity;
import ir.mehdi.mycleanarch.infrastructure.controllers.store.StoreResponse;
import ir.mehdi.mycleanarch.usecases.cousine.GetAllCousinsUseCase;
import ir.mehdi.mycleanarch.domain.usecases.UseCaseExecutor;
import ir.mehdi.mycleanarch.usecases.cousine.GetStoresByCousineUseCase;
import ir.mehdi.mycleanarch.usecases.cousine.SearchCousineByNameUseCase;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/cousin")
public class CousinController {

    private final UseCaseExecutor useCaseExecutor;
    private final GetAllCousinsUseCase getAllCousinsUseCase;
    private final SearchCousineByNameUseCase searchCousineByNameUseCase;
    private final GetStoresByCousineUseCase getStoresByCousineUseCase;

    public CousinController(
            UseCaseExecutor useCaseExecutor,
            GetAllCousinsUseCase getAllCousinsUseCase, SearchCousineByNameUseCase searchCousineByNameUseCase, GetStoresByCousineUseCase getStoresByCousineUseCase) {
        this.useCaseExecutor = useCaseExecutor;
        this.getAllCousinsUseCase = getAllCousinsUseCase;
        this.searchCousineByNameUseCase = searchCousineByNameUseCase;
        this.getStoresByCousineUseCase = getStoresByCousineUseCase;
    }

    @GetMapping("/{id}/stores")
    public CompletableFuture<List<StoreResponse>> getStoresByCousinId(@PathVariable Long id) {
        return useCaseExecutor.execute(
                getStoresByCousineUseCase,
                new GetStoresByCousineUseCase.InputValues(new Identity(id)),
                (outputValues) -> StoreResponse.from(outputValues.getStores()));
    }

    @GetMapping
    public CompletableFuture<List<CousinResponse>> getAllCousins() {
        return useCaseExecutor.execute(
                getAllCousinsUseCase,
                new GetAllCousinsUseCase.InputValues(),
                (outputValues) -> CousinResponse.from(outputValues.getCousins()));
    }

    @GetMapping("/search/{text}")
    public CompletableFuture<List<CousinResponse>> getAllCousinsByNameMatching(@PathVariable String text) {
        return useCaseExecutor.execute(
                searchCousineByNameUseCase,
                new SearchCousineByNameUseCase.InputValues(text),
                (outputValues) -> CousinResponse.from(outputValues.getCousins()));
    }
}
