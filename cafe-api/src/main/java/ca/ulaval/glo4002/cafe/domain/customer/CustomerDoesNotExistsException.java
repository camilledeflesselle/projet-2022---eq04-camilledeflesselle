package ca.ulaval.glo4002.cafe.domain.customer;

public class CustomerDoesNotExistsException extends RuntimeException {
    public CustomerDoesNotExistsException() {
        super("The customer does not exist.");
    }
}
