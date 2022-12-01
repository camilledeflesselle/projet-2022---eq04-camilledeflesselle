package ca.ulaval.glo4002.cafe.domain.customer;

public class DuplicateCustomerException extends RuntimeException {
    public DuplicateCustomerException() {
        super("The customer cannot visit the café multiple times in the same day.");
    }
}
