package ca.ulaval.glo4002.cafe.application.cooking;

import ca.ulaval.glo4002.cafe.application.inventory.IngredientType;
import ca.ulaval.glo4002.cafe.application.menu.CoffeeType;
import ca.ulaval.glo4002.cafe.domain.inventory.Ingredient;
import ca.ulaval.glo4002.cafe.domain.recipe.Recipe;

import java.util.List;

public class RecipeFactory {
    public Recipe createRecipe(CoffeeType coffeeType) {
        Recipe recipe;
        List<Ingredient> ingredients;

        switch (coffeeType) {
            case AMERICANO -> {
                ingredients = List.of(
                        new Ingredient(IngredientType.ESPRESSO, 50),
                        new Ingredient(IngredientType.WATER, 50)
                );
                recipe = new Recipe(coffeeType, ingredients);
            }
            case DARK_ROAST -> {
                ingredients = List.of(
                        new Ingredient(IngredientType.ESPRESSO, 50),
                        new Ingredient(IngredientType.WATER, 40),
                        new Ingredient(IngredientType.CHOCOLATE, 10),
                        new Ingredient(IngredientType.MILK, 10)
                );
                recipe = new Recipe(coffeeType, ingredients);
            }
            case CAPPUCCINO -> {
                ingredients = List.of(
                        new Ingredient(IngredientType.ESPRESSO, 50),
                        new Ingredient(IngredientType.WATER, 40),
                        new Ingredient(IngredientType.MILK, 10)
                );
                recipe = new Recipe(coffeeType, ingredients);
            }
            case ESPRESSO -> {
                ingredients = List.of(
                        new Ingredient(IngredientType.ESPRESSO, 60)
                );
                recipe = new Recipe(coffeeType, ingredients);
            }
            case FLAT_WHITE, LATTE -> {
                ingredients = List.of(
                        new Ingredient(IngredientType.ESPRESSO, 50),
                        new Ingredient(IngredientType.MILK, 50)
                );
                recipe = new Recipe(coffeeType, ingredients);
            }
            case MACCHIATO -> {
                ingredients = List.of(
                        new Ingredient(IngredientType.ESPRESSO, 80),
                        new Ingredient(IngredientType.MILK, 20)
                );
                recipe = new Recipe(coffeeType, ingredients);
            }
            case MOCHA -> {
                ingredients = List.of(
                        new Ingredient(IngredientType.ESPRESSO, 50),
                        new Ingredient(IngredientType.MILK, 40),
                        new Ingredient(IngredientType.CHOCOLATE, 10)
                );
                recipe = new Recipe(coffeeType, ingredients);
            }
            default -> throw new IllegalArgumentException("Invalid coffee type");
        }
    return recipe;
    }
}
