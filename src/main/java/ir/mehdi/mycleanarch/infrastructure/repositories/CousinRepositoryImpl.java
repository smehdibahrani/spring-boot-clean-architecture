package ir.mehdi.mycleanarch.infrastructure.repositories;

import ir.mehdi.mycleanarch.domain.models.Cousin;
import ir.mehdi.mycleanarch.domain.models.Identity;
import ir.mehdi.mycleanarch.domain.models.Store;
import ir.mehdi.mycleanarch.domain.repositories.CousinRepository;
import ir.mehdi.mycleanarch.infrastructure.entities.CousinEntity;
import ir.mehdi.mycleanarch.infrastructure.entities.StoreEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class CousinRepositoryImpl implements CousinRepository {

    private final JpaCousinRepository jpaCousinRepository;

    public CousinRepositoryImpl(JpaCousinRepository jpaCousinRepository) {
        this.jpaCousinRepository = jpaCousinRepository;
    }

    @Override
    public List<Store> getStoresById(Identity id) {
        return jpaCousinRepository
                .findStoresById(id.getNumber())
                .parallelStream()
                .map(StoreEntity::fromThis)
                .collect(Collectors.toList());
    }

    @Override
    public List<Cousin> getAll() {
        return jpaCousinRepository
                .findAll()
                .parallelStream()
                .map(CousinEntity::fromThis)
                .collect(Collectors.toList());
    }

    @Override
    public List<Cousin> searchByName(String search) {
        return jpaCousinRepository
                .findByNameContainingIgnoreCase(search)
                .parallelStream()
                .map(CousinEntity::fromThis)
                .collect(Collectors.toList());
    }
}
