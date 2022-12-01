package ca.ulaval.glo4002.cafe.infrastructure.rest.validators.config;

public class InvalidCountryException extends RuntimeException {
    public InvalidCountryException() {
        super("The specified country is invalid.");
    }
}
