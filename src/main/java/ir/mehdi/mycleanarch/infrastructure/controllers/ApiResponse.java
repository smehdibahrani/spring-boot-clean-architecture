package ir.mehdi.mycleanarch.infrastructure.controllers;

import lombok.Value;

@Value
public class ApiResponse {
    private final Boolean success;
    private final String message;
}
