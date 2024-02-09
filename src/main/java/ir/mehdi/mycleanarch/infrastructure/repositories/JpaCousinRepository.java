package ir.mehdi.mycleanarch.infrastructure.repositories;

import ir.mehdi.mycleanarch.infrastructure.entities.CousinEntity;
import ir.mehdi.mycleanarch.infrastructure.entities.StoreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface JpaCousinRepository extends JpaRepository<CousinEntity, Long> {
    List<CousinEntity> findByNameContainingIgnoreCase(String search);

    @Query("select s from cousin c join c.stores s where c.id = ?1")
    List<StoreEntity> findStoresById(Long id);
}
