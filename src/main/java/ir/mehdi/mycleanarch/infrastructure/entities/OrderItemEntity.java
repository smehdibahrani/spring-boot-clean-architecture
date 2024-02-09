package ir.mehdi.mycleanarch.infrastructure.entities;

import ir.mehdi.mycleanarch.domain.models.OrderItem;
import ir.mehdi.mycleanarch.domain.models.Identity;
import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static ir.mehdi.mycleanarch.infrastructure.common.utils.IdConverter.convertId;

@AllArgsConstructor
@Entity(name = "orderItem")
@EqualsAndHashCode(of = {"order", "product", "price", "quantity", "total"})
@Getter
@NoArgsConstructor
@Setter
@Table(name = "order_item")
@ToString(of = {"order", "product", "price", "quantity", "total"})
public class OrderItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private OrderEntity order;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private ProductEntity product;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private Double total;

    // TODO: test
    public static Set<OrderItemEntity> from(List<OrderItem> orderItems) {
        return orderItems
                .stream()
                .map(OrderItemEntity::from)
                .collect(Collectors.toSet());
    }

    // TODO: test
    public static OrderItemEntity from(OrderItem orderItem) {
        return new OrderItemEntity(
                convertId(orderItem.getId()),
                null,
                ProductEntity.from(orderItem.getProduct()),
                orderItem.getProduct().getPrice(),
                orderItem.getQuantity(),
                orderItem.getTotal()
        );
    }

    // TODO: test
    public static OrderItemEntity newInstance(ProductEntity productData, Integer quantity) {
        return new OrderItemEntity(
                null,
                null,
                productData,
                productData.getPrice(),
                quantity,
                quantity * productData.getPrice()
        );
    }

    // TODO: test
    public OrderItem fromThis() {
        return new OrderItem(
                new Identity(id),
                quantity,
                product.fromThis(),
                total
        );
    }
}
