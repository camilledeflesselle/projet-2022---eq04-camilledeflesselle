package ca.ulaval.glo4002.cafe.domain.recipe;

import ca.ulaval.glo4002.cafe.domain.inventory.IngredientId;
import ca.ulaval.glo4002.cafe.domain.inventory.Ingredients;
import ca.ulaval.glo4002.cafe.domain.menu.MenuItemId;

public class Recipe {
    private final Ingredients ingredients;
    private final MenuItemId id;

    public Recipe(MenuItemId id, Ingredients ingredients) {
        this.id = id;
        this.ingredients = ingredients;
    }

    public MenuItemId getName() {
        return this.id;
    }

    public Ingredients getIngredients() {
        return this.ingredients;
    }

    public void cookWith(Ingredients ingredients) {
        ingredients.removeIngredients(this.ingredients);
    }

    public boolean isCustom() {
        return this.id.isCustom();
    }

    public boolean contains(IngredientId ingredientId) {
        return this.ingredients.contains(ingredientId);
    }

    public int getNumberOfIngredients() {
        return this.ingredients.size();
    }
}
