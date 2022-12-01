package ca.ulaval.glo4002.cafe.domain.recipe;

import ca.ulaval.glo4002.cafe.domain.inventory.IInventoryRepository;
import ca.ulaval.glo4002.cafe.domain.inventory.Ingredient;

import java.util.List;
import java.util.Map;

public class Recipe {
    private final String name;
    private final List<Ingredient> ingredients;

    public Recipe(String name, List<Ingredient> ingredients) {
        this.name = name;
        this.ingredients = ingredients;
    }

    public String getName() {
        return this.name;
    }

    public List<Ingredient> getIngredients() {
        return this.ingredients;
    }

    public void makeRecipe(IInventoryRepository inventoryRepository) {
        for (Ingredient ingredientNeeded : this.ingredients) {
            Ingredient ingredient = inventoryRepository.findByName(ingredientNeeded.getName());
            ingredient.use(ingredientNeeded.getQuantity());
        }
    }
}
