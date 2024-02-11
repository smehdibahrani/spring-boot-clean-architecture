package ir.mehdi.mycleanarch.infrastructure.controllers.customer;

import lombok.Value;

@Value
public class AuthenticationResponse {
    private boolean success = true;
    private String token;
}
