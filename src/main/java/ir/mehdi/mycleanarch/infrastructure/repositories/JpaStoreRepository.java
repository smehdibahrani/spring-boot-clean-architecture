package ir.mehdi.mycleanarch.infrastructure.repositories;


import ir.mehdi.mycleanarch.infrastructure.entities.ProductEntity;
import ir.mehdi.mycleanarch.infrastructure.entities.StoreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface JpaStoreRepository extends JpaRepository<StoreEntity, Long> {
    List<StoreEntity> findByNameContainingIgnoreCase(String name);

    @Query("select p from store s join s.products p where s.id = ?1")
    List<ProductEntity> findProductsById(Long id);
}
