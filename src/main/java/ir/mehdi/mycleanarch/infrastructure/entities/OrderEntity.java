package ir.mehdi.mycleanarch.infrastructure.entities;

import ir.mehdi.mycleanarch.domain.enums.Status;
import ir.mehdi.mycleanarch.domain.models.Identity;
import ir.mehdi.mycleanarch.domain.models.Order;
import ir.mehdi.mycleanarch.domain.models.OrderItem;
import lombok.*;

import javax.persistence.*;
import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static ir.mehdi.mycleanarch.infrastructure.common.utils.IdConverter.convertId;


@Entity(name = "order")
@EqualsAndHashCode(of = {"customer", "store", "total", "status", "createdAt", "updatedAt"})
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Table(name = "orders")
@ToString(of = {"customer", "store", "total", "status", "createdAt", "updatedAt"})
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private CustomerEntity customer;

    @ManyToOne
    @JoinColumn(name = "store_id", nullable = false)
    private StoreEntity store;

    @OneToMany(
            fetch = FetchType.EAGER,
            mappedBy = "order",
            cascade = {CascadeType.MERGE, CascadeType.PERSIST},
            orphanRemoval = true
    )
    private Set<OrderItemEntity> orderItems;

    @Column(nullable = false)
    private Double total;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    // TODO: test
    public void addOrderItem(OrderItemEntity orderItem) {
        if (this.orderItems == null) {
            this.orderItems = new HashSet<>();
        }

        orderItem.setOrder(this);
        this.orderItems.add(orderItem);

        this.calculateTotal();
    }

    // TODO: test
    public Order fromThis() {
        return new Order(
                new Identity(id),
                status,
                customer.fromThis(),
                store.fromThis(),
                fromOrderItemData(),
                total,
                createdAt,
                updatedAt
        );
    }

    private List<OrderItem> fromOrderItemData() {
        return orderItems
                .stream()
                .map(OrderItemEntity::fromThis)
                .collect(Collectors.toList());
    }

    // TODO: test
    public static OrderEntity newInstance(CustomerEntity customer,
                                          StoreEntity store,
                                          Set<OrderItemEntity> orderItems) {
        OrderEntity order = new OrderEntity(
                null,
                customer,
                store,
                null,
                0d,
                Status.OPEN,
                Instant.now(),
                Instant.now()
        );

        orderItems.forEach(order::addOrderItem);

        return order;
    }

    private void calculateTotal() {
        this.total = this.orderItems
                .stream()
                .mapToDouble(OrderItemEntity::getTotal)
                .sum();
    }

    // TODO: test
    public static OrderEntity from(Order order) {
        OrderEntity orderData = new OrderEntity(
                convertId(order.getId()),
                CustomerEntity.from(order.getCustomer()),
                StoreEntity.from(order.getStore()),
                new HashSet<>(),
                order.getTotal(),
                order.getStatus(),
                order.getCreatedAt(),
                order.getUpdatedAt()
        );

        OrderItemEntity
                .from(order.getOrderItems())
                .forEach(orderData::addOrderItem);

        return orderData;
    }
}
