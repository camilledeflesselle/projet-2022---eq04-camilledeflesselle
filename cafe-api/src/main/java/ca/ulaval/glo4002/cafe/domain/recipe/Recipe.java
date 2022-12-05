package ca.ulaval.glo4002.cafe.domain.recipe;

import ca.ulaval.glo4002.cafe.application.menu.CoffeeFactory;
import ca.ulaval.glo4002.cafe.application.menu.CoffeeType;
import ca.ulaval.glo4002.cafe.domain.inventory.IInventoryRepository;
import ca.ulaval.glo4002.cafe.domain.inventory.Ingredient;

import java.util.List;

public class Recipe {
    private final List<Ingredient> ingredients;
    private final CoffeeType name;

    public Recipe(CoffeeType coffeeType, List<Ingredient> ingredients) {
        this.name = coffeeType;
        this.ingredients = ingredients;
    }

    public CoffeeType getName() {
        return this.name;
    }

    public List<Ingredient> getIngredients() {
        return this.ingredients;
    }

    public void makeRecipe(IInventoryRepository inventoryRepository) {
        for (Ingredient ingredientNeeded : this.ingredients) {
            Ingredient ingredient = inventoryRepository.find(ingredientNeeded.getId());
            ingredient.use(ingredientNeeded.getQuantity());
        }
    }
}
