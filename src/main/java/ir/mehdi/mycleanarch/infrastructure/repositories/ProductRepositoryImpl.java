package ir.mehdi.mycleanarch.infrastructure.repositories;

import ir.mehdi.mycleanarch.domain.models.Identity;
import ir.mehdi.mycleanarch.domain.models.Product;
import ir.mehdi.mycleanarch.domain.repositories.ProductRepository;
import ir.mehdi.mycleanarch.infrastructure.entities.ProductEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class ProductRepositoryImpl implements ProductRepository {
    private final JpaProductRepository repository;

    public ProductRepositoryImpl(JpaProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Product> getAll() {
        return repository
                .findAll()
                .stream()
                .map(ProductEntity::fromThis)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Product> getById(Identity id) {
        return repository
                .findById(id.getNumber())
                .map(ProductEntity::fromThis);
    }

    @Override
    public List<Product> searchByNameOrDescription(String searchText) {
        return repository
                .findByNameContainingOrDescriptionContainingAllIgnoreCase(searchText, searchText)
                .stream()
                .map(ProductEntity::fromThis)
                .collect(Collectors.toList());
    }

    @Override
    public List<Product> searchProductsByStoreAndProductsId(Identity storeId, List<Identity> productsId) {
        return repository
                .findByStoreIdAndIdIsIn(storeId.getNumber(), createListOfLong(productsId))
                .stream()
                .map(ProductEntity::fromThis)
                .collect(Collectors.toList());
    }

    private List<Long> createListOfLong(List<Identity> productsId) {
        return productsId
                .stream()
                .map(Identity::getNumber)
                .collect(Collectors.toList());
    }
}
