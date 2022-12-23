package ca.ulaval.glo4002.cafe.domain.inventory;

import java.util.HashMap;
import java.util.Map;

public class Inventory {
    private final Map<IngredientId, Integer> inventory;

    public Inventory() {
        this.inventory = new HashMap<>();
    }

    public Map<IngredientId, Integer> getInventory() {
        return this.inventory;
    }

    public void addIngredient(Ingredient ingredient) {
        this.inventory.put(ingredient.getId(), ingredient.getQuantity());
    }

    public void addQuantity(Ingredient ingredient) {
        int oldQuantity = this.inventory.get(ingredient.getId());
        this.inventory.put(ingredient.getId(), oldQuantity + ingredient.getQuantity());
    }

    public int findIngredientQuantity(IngredientId ingredientId) {
        return this.inventory.get(ingredientId);
    }

    public void reset() {
        this.inventory.replaceAll((ingredientId, quantity) -> 0);
    }

    public void useIngredient(Ingredient anExistingIngredient) {
        int oldQuantity = this.inventory.get(anExistingIngredient.getId());
        this.inventory.put(anExistingIngredient.getId(), oldQuantity - anExistingIngredient.getQuantity());
    }

    public boolean contains(String ingredientName) {
        return this.inventory.containsKey(new IngredientId(ingredientName));
    }
}
