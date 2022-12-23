package ca.ulaval.glo4002.cafe.domain.inventory;

import ca.ulaval.glo4002.cafe.domain.cooking.InsufficentIngredientsException;

public class Ingredient {
    private final IngredientId id;
    private int quantity;

    public Ingredient(IngredientId id, int quantity) {
        this.id = id;
        this.quantity = quantity;
    }

    public IngredientId getId() {
        return this.id;
    }

    public void use(Integer quantity) {
        this.quantity -= quantity;
    }

    public Integer getQuantity() {
        return this.quantity;
    }

    public void checkIfEnough(Integer quantity) {
        if (quantity > this.quantity) {
            throw new InsufficentIngredientsException();
        }
    }
}
