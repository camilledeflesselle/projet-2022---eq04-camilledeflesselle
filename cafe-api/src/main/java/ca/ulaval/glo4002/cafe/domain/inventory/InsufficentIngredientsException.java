package ca.ulaval.glo4002.cafe.domain.inventory;

public class InsufficentIngredientsException extends RuntimeException {
    public InsufficentIngredientsException() {
        super("We lack the necessary number of ingredients to fulfill your order.");
    }
}
