package ir.mehdi.mycleanarch.infrastructure.exceptions;

public class EmailAlreadyUsedException extends DomainException {
    public EmailAlreadyUsedException(String message) {
        super(message);
    }
}
