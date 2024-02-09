package ir.mehdi.mycleanarch.infrastructure.repositories;


import ir.mehdi.mycleanarch.infrastructure.entities.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaProductRepository extends JpaRepository<ProductEntity, Long> {
    List<ProductEntity> findByNameContainingOrDescriptionContainingAllIgnoreCase(String name, String description);

    List<ProductEntity> findByStoreIdAndIdIsIn(Long id, List<Long> ids);
}
