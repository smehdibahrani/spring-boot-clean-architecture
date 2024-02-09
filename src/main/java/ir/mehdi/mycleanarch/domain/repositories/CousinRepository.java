package ir.mehdi.mycleanarch.domain.repositories;


import ir.mehdi.mycleanarch.domain.models.Cousin;
import ir.mehdi.mycleanarch.domain.models.Identity;
import ir.mehdi.mycleanarch.domain.models.Store;

import java.util.List;

public interface CousinRepository {
    List<Store> getStoresById(Identity id);

    List<Cousin> getAll();

    List<Cousin> searchByName(String search);
}
