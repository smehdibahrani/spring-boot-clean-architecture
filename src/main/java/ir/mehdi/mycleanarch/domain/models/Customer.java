package ir.mehdi.mycleanarch.domain.models;

import lombok.*;

@AllArgsConstructor
@EqualsAndHashCode
@Getter
@NoArgsConstructor
@Setter
@ToString
public class Customer {
    private Identity id;
    private String name;
    private String email;
    private String address;
    private String password;

    public static Customer newInstance(String name, String email, String address, String password) {
        return new Customer(
                Identity.nothing(),
                name,
                email,
                address,
                password
        );
    }
}
