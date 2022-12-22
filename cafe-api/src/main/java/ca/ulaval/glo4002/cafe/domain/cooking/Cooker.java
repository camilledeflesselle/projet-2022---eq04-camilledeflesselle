package ca.ulaval.glo4002.cafe.domain.cooking;

import ca.ulaval.glo4002.cafe.domain.inventory.IngredientId;
import ca.ulaval.glo4002.cafe.domain.inventory.Inventory;
import ca.ulaval.glo4002.cafe.domain.order.Order;
import ca.ulaval.glo4002.cafe.domain.recipe.RecipeRepository;

import java.util.Map;

public class Cooker {

    public Cooker() {
    }

    public void cook(Order newOrder, Inventory inventory, RecipeRepository recipeRepository) {
        newOrder.make(recipeRepository, inventory);
    }

    public void checkIfEnoughIngredients(Order newOrder, Inventory inventory, RecipeRepository recipeRepository) {
        Map<IngredientId, Integer> ingredientsQuantities = newOrder.getAllIngredientsQuantities(recipeRepository);
        ingredientsQuantities.forEach((ingredientId, quantity) -> {
            if (inventory.findIngredientQuantity(ingredientId) < quantity) {
                throw new InsufficentIngredientsException();
            }
        });
    }
}

