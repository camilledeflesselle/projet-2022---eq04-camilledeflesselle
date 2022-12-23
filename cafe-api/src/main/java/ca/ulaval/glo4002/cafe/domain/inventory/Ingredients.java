package ca.ulaval.glo4002.cafe.domain.inventory;

import java.util.HashMap;
import java.util.Map;

public class Ingredients {
    private final Map<IngredientId, Integer> ingredients;

    public Ingredients() {
        this.ingredients = new HashMap<>();
    }

    public Map<IngredientId, Integer> getIngredients() {
        return this.ingredients;
    }

    public void addIngredientsQuantitiesFrom(Ingredients newIngredients) {
        Map<IngredientId, Integer> ingredientsToAdd = newIngredients.getIngredients();
        for (IngredientId ingredientId : ingredientsToAdd.keySet()) {
            this.addIngredient(ingredientId, ingredientsToAdd.get(ingredientId));
        }
    }

    public void removeIngredients(Ingredients ingredientsToRemove) {
        for (IngredientId ingredientId : ingredientsToRemove.getIngredients().keySet()) {
            this.ingredients.replace(ingredientId, this.findIngredientQuantity(ingredientId) - ingredientsToRemove.findIngredientQuantity(ingredientId));
        }
    }


    public Integer findIngredientQuantity(IngredientId ingredientId) {
        return this.ingredients.get(ingredientId);
    }

    public void addIngredient(IngredientId ingredientId, Integer quantity) {
        if (this.ingredients.containsKey(ingredientId)) {
            this.ingredients.put(ingredientId, this.ingredients.get(ingredientId) + quantity);
        } else {
            this.ingredients.put(ingredientId, quantity);
        }
    }

    public boolean contains(IngredientId ingredientId) {
        return this.ingredients.containsKey(ingredientId);
    }

    public int size() {
        return this.ingredients.size();
    }

    public boolean isEmpty() {
        return this.ingredients.isEmpty();
    }
}
