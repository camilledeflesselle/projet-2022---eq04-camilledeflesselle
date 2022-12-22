package ca.ulaval.glo4002.cafe.ui.rest.assemblers.config;

public class InvalidCountryException extends RuntimeException {
    public InvalidCountryException() {
        super("The specified country is invalid.");
    }
}
