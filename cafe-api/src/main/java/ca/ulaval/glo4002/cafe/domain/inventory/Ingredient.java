package ca.ulaval.glo4002.cafe.domain.inventory;

import ca.ulaval.glo4002.cafe.application.inventory.IngredientType;
import ca.ulaval.glo4002.cafe.domain.cooking.InsufficentIngredientsException;

public class Ingredient {
    private final IngredientId name;
    private int quantity;

    public Ingredient(String name, int quantity) {
        this.name = new IngredientId(name);
        this.quantity = quantity;
    }

    public static void resetIngredientCount() {
    }

    public IngredientId getName() {
        return this.name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof Ingredient ingredient)) return false;
        return this.name.equals(ingredient.name);
    }

    @Override
    public int hashCode() {
        return this.name.hashCode();
    }

    public void use(Integer quantity) {
        if (quantity > this.quantity) {
            throw new InsufficentIngredientsException();
        }
        this.quantity -= quantity;
    }

    public Integer getQuantity() {
        return this.quantity;
    }
}
