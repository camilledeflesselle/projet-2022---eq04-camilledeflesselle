package ca.ulaval.glo4002.cafe.ui.rest.assemblers.config;

public class InvalidMenuOrderException extends RuntimeException {
    public InvalidMenuOrderException() {
        super("An item ordered is not on the menu.");
    }

}
