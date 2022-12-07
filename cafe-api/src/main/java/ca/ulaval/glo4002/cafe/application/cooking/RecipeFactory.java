package ca.ulaval.glo4002.cafe.application.cooking;

import ca.ulaval.glo4002.cafe.application.inventory.IngredientType;
import ca.ulaval.glo4002.cafe.application.menu.CoffeeType;
import ca.ulaval.glo4002.cafe.domain.inventory.Ingredient;
import ca.ulaval.glo4002.cafe.domain.inventory.IngredientId;
import ca.ulaval.glo4002.cafe.domain.recipe.Recipe;

import java.util.List;

public class RecipeFactory {
    public Recipe createRecipe(CoffeeType coffeeType) {
        Recipe recipe;
        List<Ingredient> ingredients;

        IngredientId milk = new IngredientId(IngredientType.MILK.getLabel());
        IngredientId water = new IngredientId(IngredientType.WATER.getLabel());
        IngredientId espresso = new IngredientId(IngredientType.ESPRESSO.getLabel());
        IngredientId chocolate = new IngredientId(IngredientType.CHOCOLATE.getLabel());

        switch (coffeeType) {
            case AMERICANO -> {
                ingredients = List.of(
                        new Ingredient(espresso, 50),
                        new Ingredient(water, 50)
                );
                recipe = new Recipe(coffeeType.getId(), ingredients);
            }
            case DARK_ROAST -> {
                ingredients = List.of(
                        new Ingredient(espresso, 40),
                        new Ingredient(water, 40),
                        new Ingredient(chocolate, 10),
                        new Ingredient(milk, 10)
                );
                recipe = new Recipe(coffeeType.getId(), ingredients);
            }
            case CAPPUCCINO -> {
                ingredients = List.of(
                        new Ingredient(espresso, 50),
                        new Ingredient(water, 40),
                        new Ingredient(milk, 10)
                );
                recipe = new Recipe(coffeeType.getId(), ingredients);
            }
            case ESPRESSO -> {
                ingredients = List.of(
                        new Ingredient(espresso, 60)
                );
                recipe = new Recipe(coffeeType.getId(), ingredients);
            }
            case FLAT_WHITE, LATTE -> {
                ingredients = List.of(
                        new Ingredient(espresso, 50),
                        new Ingredient(milk, 50)
                );
                recipe = new Recipe(coffeeType.getId(), ingredients);
            }
            case MACCHIATO -> {
                ingredients = List.of(
                        new Ingredient(espresso, 80),
                        new Ingredient(milk, 20)
                );
                recipe = new Recipe(coffeeType.getId(), ingredients);
            }
            case MOCHA -> {
                ingredients = List.of(
                        new Ingredient(espresso, 50),
                        new Ingredient(milk, 40),
                        new Ingredient(chocolate, 10)
                );
                recipe = new Recipe(coffeeType.getId(), ingredients);
            }
            default -> throw new IllegalArgumentException("Invalid coffee type");
        }
        return recipe;
    }
}
