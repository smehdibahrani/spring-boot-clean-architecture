package ir.mehdi.mycleanarch.infrastructure.config;


import ir.mehdi.mycleanarch.domain.repositories.*;
import ir.mehdi.mycleanarch.usecases.cousine.GetAllCousinsUseCase;
import ir.mehdi.mycleanarch.usecases.cousine.GetStoresByCousineUseCase;
import ir.mehdi.mycleanarch.usecases.cousine.SearchCousineByNameUseCase;
import ir.mehdi.mycleanarch.usecases.customer.CreateCustomerUseCase;
import ir.mehdi.mycleanarch.usecases.order.*;
import ir.mehdi.mycleanarch.usecases.product.GetAllProductsUseCase;
import ir.mehdi.mycleanarch.usecases.product.GetProductUseCase;
import ir.mehdi.mycleanarch.usecases.product.GetProductsByStoreAndProductsIdUseCase;
import ir.mehdi.mycleanarch.usecases.product.SearchProductsByNameOrDescriptionUseCase;
import ir.mehdi.mycleanarch.usecases.store.GetAllStoresUseCase;
import ir.mehdi.mycleanarch.usecases.store.GetProductsByStoreUseCase;
import ir.mehdi.mycleanarch.usecases.store.GetStoreUseCase;
import ir.mehdi.mycleanarch.usecases.store.SearchStoresByNameUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Module {

    @Bean
    public DeliveryOrderUseCase deliveryOrderUseCase(OrderRepository repository) {
        return new DeliveryOrderUseCase(repository);
    }

    @Bean
    public PayOrderUseCase payOrderUseCase(OrderRepository repository) {
        return new PayOrderUseCase(repository);
    }

    @Bean
    public DeleteOrderUseCase deleteOrderUseCase(OrderRepository repository) {
        return new DeleteOrderUseCase(repository);
    }

    @Bean
    public GetCustomerOrderUseCase getCustomerOrderUseCase(GetOrderUseCase getOrderUseCase) {
        return new GetCustomerOrderUseCase(getOrderUseCase);
    }

    @Bean
    public GetOrderUseCase getOrderUseCase(OrderRepository repository) {
        return new GetOrderUseCase(repository);
    }

    @Bean
    public CreateOrderUseCase createOrderUseCase(GetProductsByStoreAndProductsIdUseCase getProductsByStoreAndProductsIdUseCase,
                                                 OrderRepository repository) {
        return new CreateOrderUseCase(getProductsByStoreAndProductsIdUseCase, repository);
    }

    @Bean
    public GetProductsByStoreAndProductsIdUseCase getProductsByStoreAndProductsIdUseCase(ProductRepository repository) {
        return new GetProductsByStoreAndProductsIdUseCase(repository);
    }

    @Bean
    public CreateCustomerUseCase createCustomerUseCase(CustomerRepository repository) {
        return new CreateCustomerUseCase(repository);
    }

    @Bean
    public SearchProductsByNameOrDescriptionUseCase searchProductsByNameUseCase(ProductRepository repository) {
        return new SearchProductsByNameOrDescriptionUseCase(repository);
    }

    @Bean
    public GetProductUseCase getProductUseCase(ProductRepository repository) {
        return new GetProductUseCase(repository);
    }

    @Bean
    public GetAllProductsUseCase getAllProductsUseCase(ProductRepository repository) {
        return new GetAllProductsUseCase(repository);
    }

    @Bean
    public GetProductsByStoreUseCase getProductsByStoreIdentityUseCase(StoreRepository repository) {
        return new GetProductsByStoreUseCase(repository);
    }

    @Bean
    public GetStoreUseCase getStoreUseCase(StoreRepository repository) {
        return new GetStoreUseCase(repository);
    }

    @Bean
    public SearchStoresByNameUseCase searchStoresByNameUseCase(StoreRepository repository) {
        return new SearchStoresByNameUseCase(repository);
    }

    @Bean
    public GetAllStoresUseCase getAllStoresUseCase(StoreRepository repository) {
        return new GetAllStoresUseCase(repository);
    }

    @Bean
    public GetStoresByCousineUseCase getStoresByCousineUseCase(CousinRepository repository) {
        return new GetStoresByCousineUseCase(repository);
    }

    @Bean
    public GetAllCousinsUseCase getAllCousinsUseCase(CousinRepository repository) {
        return new GetAllCousinsUseCase(repository);
    }

    @Bean
    public SearchCousineByNameUseCase searchCousineByNameUseCase(CousinRepository repository) {
        return new SearchCousineByNameUseCase(repository);
    }
}
