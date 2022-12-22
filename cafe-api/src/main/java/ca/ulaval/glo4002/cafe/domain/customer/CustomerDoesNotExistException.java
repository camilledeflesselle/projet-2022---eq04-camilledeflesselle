package ca.ulaval.glo4002.cafe.domain.customer;

public class CustomerDoesNotExistException extends RuntimeException {
    public CustomerDoesNotExistException() {
        super("The customer does not exist.");
    }
}
