package ca.ulaval.glo4002.cafe.domain.cooking;

import ca.ulaval.glo4002.cafe.domain.inventory.IngredientId;
import ca.ulaval.glo4002.cafe.domain.inventory.InsufficentIngredientsException;
import ca.ulaval.glo4002.cafe.domain.inventory.Inventory;
import ca.ulaval.glo4002.cafe.domain.order.Order;
import ca.ulaval.glo4002.cafe.domain.recipe.IRecipeRepository;

import java.util.Map;

public class Cooker {
    private final IRecipeRepository recipeRepository;
    private final Inventory inventory;

    public Cooker(IRecipeRepository recipeRepository, Inventory inventory) {
        this.recipeRepository = recipeRepository;
        this.inventory = inventory;
    }

    public void cook(Order newOrder) {
        newOrder.make(recipeRepository, inventory);
    }

    public void checkIfEnoughIngredientsForOrder(Order newOrder) {
        Map<IngredientId, Integer> ingredientsQuantities = newOrder.getAllIngredientsQuantities(recipeRepository);
        ingredientsQuantities.forEach((ingredientId, quantity) -> {
            if (inventory.findIngredientQuantity(ingredientId) < quantity) {
                throw new InsufficentIngredientsException();
            }
        });
    }

    public Inventory getInventory() {
        return this.inventory;
    }
}

