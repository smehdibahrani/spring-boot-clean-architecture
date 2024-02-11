package ir.mehdi.mycleanarch.infrastructure.controllers.customer;

import ir.mehdi.mycleanarch.domain.models.Customer;
import lombok.Value;

@Value
public class CustomerResponse {
    private final String name;
    private final String email;
    private final String address;

    public static CustomerResponse from(Customer customer) {
        return new CustomerResponse(
                customer.getName(),
                customer.getEmail(),
                customer.getAddress()
        );
    }
}
