package ir.mehdi.mycleanarch.infrastructure.entities;

import ir.mehdi.mycleanarch.domain.models.Identity;
import ir.mehdi.mycleanarch.domain.models.Store;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import static ir.mehdi.mycleanarch.infrastructure.common.utils.IdConverter.convertId;

@AllArgsConstructor
@Entity(name = "store")
@EqualsAndHashCode(of = {"name", "address"})
@Getter
@NoArgsConstructor
@Setter
@Table(name = "store")
@ToString(of = {"name", "address"})
public class StoreEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    @ManyToOne
    @JoinColumn(name = "cousin_id", nullable = false)
    private CousinEntity cousin;

    @OneToMany(mappedBy = "store")
    private Set<ProductEntity> products;

    // TODO: test
    public static StoreEntity from(Store store) {
        return new StoreEntity(
                convertId(store.getId()),
                store.getName(),
                store.getAddress(),
                CousinEntity.from(store.getCousin()),
                new HashSet<>());
    }

    // TODO: test
    public Store fromThis() {
        return new Store(
                new Identity(id),
                name,
                address,
                cousin.fromThis()
        );
    }
}
