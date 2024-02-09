package ir.mehdi.mycleanarch.infrastructure.entities;

import ir.mehdi.mycleanarch.domain.models.Cousin;
import ir.mehdi.mycleanarch.domain.models.Identity;
import lombok.*;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

import static ir.mehdi.mycleanarch.infrastructure.common.utils.IdConverter.convertId;

@AllArgsConstructor
@Entity(name = "cousin")
@EqualsAndHashCode(of = {"name"})
@Getter
@NoArgsConstructor
@Setter
@Table(name = "cousin")
@ToString(of = {"name"})
public class CousinEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @OneToMany(
            mappedBy = "cousin",
            cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private Set<StoreEntity> stores;

    // TODO: test
    public void addStore(StoreEntity store) {
        if (this.stores == null) {
            this.stores = new HashSet<>();
        }

        store.setCousin(this);
        this.stores.add(store);
    }

    // TODO: test
    public static CousinEntity newInstance(String name) {
        return new CousinEntity(null, name, new HashSet<>());
    }

    // TODO: test
    public static CousinEntity from(Cousin cousin) {
        return new CousinEntity(
                convertId(cousin.getId()),
                cousin.getName(),
                new HashSet<>()
        );
    }

    public Cousin fromThis() {
        return new Cousin(
                new Identity(id),
                name
        );
    }
}
