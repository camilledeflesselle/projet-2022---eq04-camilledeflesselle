package ca.ulaval.glo4002.cafe.application.cooking;

import ca.ulaval.glo4002.cafe.application.inventory.IngredientInLes4Fees;
import ca.ulaval.glo4002.cafe.application.menu.CoffeeType;
import ca.ulaval.glo4002.cafe.domain.inventory.IngredientId;
import ca.ulaval.glo4002.cafe.domain.inventory.Ingredients;
import ca.ulaval.glo4002.cafe.domain.recipe.Recipe;

public class RecipeFactory {

    public Recipe createRecipe(CoffeeType coffeeType) {

        Ingredients ingredients = new Ingredients();

        IngredientId milk = IngredientInLes4Fees.Milk.getId();
        IngredientId water = IngredientInLes4Fees.Water.getId();
        IngredientId chocolate = IngredientInLes4Fees.Chocolate.getId();
        IngredientId espresso = IngredientInLes4Fees.Espresso.getId();

        switch (coffeeType) {
            case Americano -> {
                ingredients.addIngredient(espresso, 50);
                ingredients.addIngredient(water, 50);
            }
            case DarkRoast -> {
                ingredients.addIngredient(espresso, 40);
                ingredients.addIngredient(water, 40);
                ingredients.addIngredient(chocolate, 10);
                ingredients.addIngredient(milk, 10);
            }
            case Cappuccino -> {
                ingredients.addIngredient(espresso, 50);
                ingredients.addIngredient(milk, 40);
                ingredients.addIngredient(milk, 10);
            }
            case Espresso -> {
                ingredients.addIngredient(espresso, 60);
            }
            case FlatWhite, Latte -> {
                ingredients.addIngredient(espresso, 50);
                ingredients.addIngredient(milk, 50);
            }
            case Macchiato -> {
                ingredients.addIngredient(espresso, 80);
                ingredients.addIngredient(milk, 20);
            }
            case Mocha -> {
                ingredients.addIngredient(espresso, 50);
                ingredients.addIngredient(milk, 40);
                ingredients.addIngredient(chocolate, 10);
            }
            default -> throw new IllegalArgumentException("Invalid coffee type");
        }
        return new Recipe(coffeeType.getId(), ingredients);
    }
}
