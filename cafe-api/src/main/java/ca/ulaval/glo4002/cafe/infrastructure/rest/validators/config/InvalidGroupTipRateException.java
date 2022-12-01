package ca.ulaval.glo4002.cafe.infrastructure.rest.validators.config;

public class InvalidGroupTipRateException extends RuntimeException {
    public InvalidGroupTipRateException() {
        super("The group tip rate must be set to a value between 0 to 100.");
    }
}
