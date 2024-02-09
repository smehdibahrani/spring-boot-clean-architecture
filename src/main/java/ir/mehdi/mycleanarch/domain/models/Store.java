package ir.mehdi.mycleanarch.domain.models;

import lombok.Value;

@Value
public class Store {
    private final Identity id;
    private final String name;
    private final String address;
    private final Cousin cousin;
}
