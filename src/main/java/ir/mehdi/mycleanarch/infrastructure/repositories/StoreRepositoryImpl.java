package ir.mehdi.mycleanarch.infrastructure.repositories;


import ir.mehdi.mycleanarch.domain.models.Identity;
import ir.mehdi.mycleanarch.domain.models.Product;
import ir.mehdi.mycleanarch.domain.models.Store;
import ir.mehdi.mycleanarch.domain.repositories.StoreRepository;
import ir.mehdi.mycleanarch.infrastructure.entities.ProductEntity;
import ir.mehdi.mycleanarch.infrastructure.entities.StoreEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class StoreRepositoryImpl implements StoreRepository {
    private final JpaStoreRepository repository;

    public StoreRepositoryImpl(JpaStoreRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Store> getAll() {
        return repository
                .findAll()
                .parallelStream()
                .map(StoreEntity::fromThis)
                .collect(Collectors.toList());
    }

    @Override
    public List<Store> searchByName(String searchText) {
        return repository
                .findByNameContainingIgnoreCase(searchText)
                .parallelStream()
                .map(StoreEntity::fromThis)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Store> getById(Identity id) {
        return repository
                .findById(id.getNumber())
                .map(StoreEntity::fromThis);
    }

    @Override
    public List<Product> getProductsById(Identity id) {
        return repository
                .findProductsById(id.getNumber())
                .stream()
                .map(ProductEntity::fromThis)
                .collect(Collectors.toList());
    }
}
