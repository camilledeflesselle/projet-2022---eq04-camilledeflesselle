package ca.ulaval.glo4002.cafe.domain.recipe;

import ca.ulaval.glo4002.cafe.domain.inventory.Ingredient;
import ca.ulaval.glo4002.cafe.domain.inventory.IngredientId;
import ca.ulaval.glo4002.cafe.domain.inventory.Inventory;
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

    public void cookWith(Inventory inventory) {
        this.ingredients.forEach(inventory::removeQuantity);
    }

    public boolean contains(IngredientId name, int quantity) {
        return this.ingredients.stream().anyMatch(ingredient -> ingredient.getId().equals(name) && ingredient.getQuantity() == quantity);
    }
}
