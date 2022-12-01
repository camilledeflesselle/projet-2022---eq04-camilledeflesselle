package ca.ulaval.glo4002.cafe.domain.bill;

public class NoBillException extends RuntimeException {
    public NoBillException() {
        super("The customer needs to do a checkout before receiving his bill.");
    }
}
