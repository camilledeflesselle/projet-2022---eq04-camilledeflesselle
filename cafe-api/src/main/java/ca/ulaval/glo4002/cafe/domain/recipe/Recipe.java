package ca.ulaval.glo4002.cafe.domain.recipe;

import ca.ulaval.glo4002.cafe.domain.inventory.IIngredientRepository;
import ca.ulaval.glo4002.cafe.domain.inventory.Ingredient;
import ca.ulaval.glo4002.cafe.domain.menu.MenuItemId;

import java.util.List;

public class Recipe {
    private final List<Ingredient> ingredients;
    private final MenuItemId id;

    public Recipe(MenuItemId id, List<Ingredient> ingredients) {
        this.id = id;
        this.ingredients = ingredients;
    }

    public MenuItemId getName() {
        return this.id;
    }

    public List<Ingredient> getIngredients() {
        return this.ingredients;
    }

    public void cookWithStorageIn(IIngredientRepository inventoryRepository) {
        this.ingredients.forEach(inventoryRepository::removeIngredient);
    }
}
