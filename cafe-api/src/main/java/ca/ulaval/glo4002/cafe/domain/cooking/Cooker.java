package ca.ulaval.glo4002.cafe.domain.cooking;

import ca.ulaval.glo4002.cafe.domain.inventory.Ingredients;
import ca.ulaval.glo4002.cafe.domain.inventory.Inventory;
import ca.ulaval.glo4002.cafe.domain.order.Order;
import ca.ulaval.glo4002.cafe.domain.recipe.RecipeRepository;

public class Cooker {

    public Cooker() {
    }

    public void cook(Order newOrder, Inventory inventory, RecipeRepository recipeRepository) {
        newOrder.make(recipeRepository, inventory);
    }

    public void checkIfEnoughIngredients(Order newOrder, Inventory inventory, RecipeRepository recipeRepository) {
        Ingredients ingredientsQuantities = newOrder.getAllIngredientsQuantities(recipeRepository);
        if (!inventory.hasMoreIngredients(ingredientsQuantities)) {
            throw new InsufficentIngredientsException();
        }
    }
}

