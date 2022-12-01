package ca.ulaval.glo4002.cafe.domain.recipe;

import ca.ulaval.glo4002.cafe.domain.inventory.Ingredient;
import java.util.Map;

public class Recipe {
    private final String name;
    private final Map<Ingredient, Integer> ingredients;

    public Recipe(String name, Map<Ingredient, Integer> ingredients) {
        this.name = name;
        this.ingredients = ingredients;
    }

    public String getName() {
        return this.name;
    }

    public Map<Ingredient, Integer> getIngredients() {
        return this.ingredients;
    }
}
