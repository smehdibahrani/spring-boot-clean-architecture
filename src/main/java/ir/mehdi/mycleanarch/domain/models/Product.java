package ir.mehdi.mycleanarch.domain.models;

import lombok.*;

@AllArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
@ToString
public class Product {
    private Identity id;
    private String name;
    private String description;
    private Double price;
    private Store store;
}
