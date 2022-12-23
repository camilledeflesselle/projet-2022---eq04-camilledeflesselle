package ca.ulaval.glo4002.cafe.application.cooking;

import ca.ulaval.glo4002.cafe.application.inventory.IngredientInLes4Fees;
import ca.ulaval.glo4002.cafe.application.menu.CoffeeType;
import ca.ulaval.glo4002.cafe.domain.inventory.Ingredient;
import ca.ulaval.glo4002.cafe.domain.inventory.IngredientId;
import ca.ulaval.glo4002.cafe.domain.recipe.Recipe;

import java.util.List;

public class RecipeFactory {

    public Recipe createRecipe(CoffeeType coffeeType) {
        Recipe recipe;
        List<Ingredient> ingredients;

        IngredientId milk = IngredientInLes4Fees.Milk.getId();
        IngredientId water = IngredientInLes4Fees.Water.getId();
        IngredientId chocolate = IngredientInLes4Fees.Chocolate.getId();
        IngredientId espresso = IngredientInLes4Fees.Espresso.getId();

        switch (coffeeType) {
            case Americano -> {
                ingredients = List.of(
                        new Ingredient(espresso, 50),
                        new Ingredient(water, 50)
                );
                recipe = new Recipe(coffeeType.getId(), ingredients);
            }
            case DarkRoast -> {
                ingredients = List.of(
                        new Ingredient(espresso, 40),
                        new Ingredient(water, 40),
                        new Ingredient(chocolate, 10),
                        new Ingredient(milk, 10)
                );
                recipe = new Recipe(coffeeType.getId(), ingredients);
            }
            case Cappuccino -> {
                ingredients = List.of(
                        new Ingredient(espresso, 50),
                        new Ingredient(water, 40),
                        new Ingredient(milk, 10)
                );
                recipe = new Recipe(coffeeType.getId(), ingredients);
            }
            case Espresso -> {
                ingredients = List.of(
                        new Ingredient(espresso, 60)
                );
                recipe = new Recipe(coffeeType.getId(), ingredients);
            }
            case FlatWhite, Latte -> {
                ingredients = List.of(
                        new Ingredient(espresso, 50),
                        new Ingredient(milk, 50)
                );
                recipe = new Recipe(coffeeType.getId(), ingredients);
            }
            case Macchiato -> {
                ingredients = List.of(
                        new Ingredient(espresso, 80),
                        new Ingredient(milk, 20)
                );
                recipe = new Recipe(coffeeType.getId(), ingredients);
            }
            case Mocha -> {
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
