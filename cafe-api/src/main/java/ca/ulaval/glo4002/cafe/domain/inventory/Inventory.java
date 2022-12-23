package ca.ulaval.glo4002.cafe.domain.inventory;

public class Inventory extends Ingredients {
    public Inventory() {
        super();
    }

    public void reset() {
        this.getIngredients().replaceAll((ingredientId, quantity) -> 0);
    }

    public boolean hasMoreIngredients(Ingredients ingredients) {
        for (IngredientId ingredientId : ingredients.getIngredients().keySet()) {
            if (this.findIngredientQuantity(ingredientId) - ingredients.findIngredientQuantity(ingredientId) < 0) {
                return false;
            }
        }
        return true;
    }
}
