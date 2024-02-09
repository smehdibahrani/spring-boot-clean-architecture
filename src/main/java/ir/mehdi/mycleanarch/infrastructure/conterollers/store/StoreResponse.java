package ir.mehdi.mycleanarch.infrastructure.conterollers.store;

import ir.mehdi.mycleanarch.domain.models.Store;
import lombok.Value;

import java.util.List;
import java.util.stream.Collectors;

import static ir.mehdi.mycleanarch.infrastructure.common.utils.IdConverter.convertId;


@Value
public class StoreResponse {
    private final Long id;
    private final String name;
    private final String address;
    private final Long cousinId;

    public static StoreResponse from(Store store) {
        return new StoreResponse(
                store.getId().getNumber(),
                store.getName(),
                store.getAddress(),
                convertId(store.getCousin().getId())
        );
    }

    public static List<StoreResponse> from(List<Store> stores) {
        return stores
                .parallelStream()
                .map(StoreResponse::from)
                .collect(Collectors.toList());
    }
}
