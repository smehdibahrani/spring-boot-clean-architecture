package ir.mehdi.mycleanarch.infrastructure.entities;

import ir.mehdi.mycleanarch.domain.models.Customer;
import ir.mehdi.mycleanarch.domain.models.Identity;
import lombok.*;

import javax.persistence.*;
import static ir.mehdi.mycleanarch.infrastructure.common.utils.IdConverter.convertId;

@AllArgsConstructor
@Entity(name = "customer")
@EqualsAndHashCode(of = {"name", "email", "address", "password"})
@Getter
@NoArgsConstructor
@Setter
@Table(name = "customer")
@ToString(of = {"name", "email", "address"})
public class CustomerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String password;

    // TODO: test
    public static CustomerEntity from(Customer customer) {
        return new CustomerEntity(
                convertId(customer.getId()),
                customer.getName(),
                customer.getEmail(),
                customer.getAddress(),
                customer.getPassword()
        );
    }

    // TODO: test
    public Customer fromThis() {
        return new Customer(
                new Identity(id),
                name,
                email,
                address,
                password
        );
    }
}
