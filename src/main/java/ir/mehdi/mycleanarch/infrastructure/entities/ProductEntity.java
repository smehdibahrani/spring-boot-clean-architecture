package ir.mehdi.mycleanarch.infrastructure.entities;

import ir.mehdi.mycleanarch.domain.models.Identity;
import ir.mehdi.mycleanarch.domain.models.Product;
import lombok.*;

import javax.persistence.*;

import static ir.mehdi.mycleanarch.infrastructure.common.utils.IdConverter.convertId;

@AllArgsConstructor
@Entity(name = "product")
@EqualsAndHashCode(of = {"name", "description", "price"})
@Getter
@NoArgsConstructor
@Setter
@Table(name = "product")
@ToString(of = {"name", "description", "price"})
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Double price;

    @ManyToOne
    @JoinColumn(name = "store_id", nullable = false)
    private StoreEntity store;

    // TODO: test
    public static ProductEntity from(Product product) {
        return new ProductEntity(
                convertId(product.getId()),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                StoreEntity.from(product.getStore())
        );
    }

    // TODO: test
    public Product fromThis() {
        return new Product(
                new Identity(id),
                name,
                description,
                price,
                store.fromThis()
        );
    }
}
