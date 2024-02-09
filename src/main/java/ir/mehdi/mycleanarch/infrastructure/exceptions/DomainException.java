package ir.mehdi.mycleanarch.infrastructure.exceptions;

public abstract class DomainException extends RuntimeException {
    public DomainException(String message) {
        super(message);
    }
}
