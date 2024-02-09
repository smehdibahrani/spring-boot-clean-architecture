package ir.mehdi.mycleanarch.domain.repositories;
import ir.mehdi.mycleanarch.domain.models.Identity;
import ir.mehdi.mycleanarch.domain.models.Product;
import ir.mehdi.mycleanarch.domain.models.Store;

import java.util.List;
import java.util.Optional;

public interface StoreRepository {
    List<Store> getAll();

    List<Store> searchByName(String searchText);

    Optional<Store> getById(Identity id);

    List<Product> getProductsById(Identity id);
}
