package ir.mehdi.mycleanarch.domain.repositories;


import ir.mehdi.mycleanarch.domain.models.Identity;
import ir.mehdi.mycleanarch.domain.models.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    List<Product> getAll();

    Optional<Product> getById(Identity id);

    List<Product> searchByNameOrDescription(String searchText);

    List<Product> searchProductsByStoreAndProductsId(Identity storeId, List<Identity> productsId);
}
