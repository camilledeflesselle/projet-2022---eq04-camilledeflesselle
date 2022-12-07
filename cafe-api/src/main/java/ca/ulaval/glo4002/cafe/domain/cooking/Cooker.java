package ca.ulaval.glo4002.cafe.domain.cooking;

import ca.ulaval.glo4002.cafe.domain.inventory.IInventoryRepository;
import ca.ulaval.glo4002.cafe.domain.inventory.IngredientId;
import ca.ulaval.glo4002.cafe.domain.inventory.InsufficentIngredientsException;
import ca.ulaval.glo4002.cafe.domain.order.Order;
import ca.ulaval.glo4002.cafe.domain.recipe.IRecipeRepository;

import java.util.Map;

public class Cooker {
    private final IRecipeRepository recipeRepository;
    private final IInventoryRepository inventoryRepository;

    public Cooker(IRecipeRepository recipeRepository, IInventoryRepository inventoryRepository) {
        this.recipeRepository = recipeRepository;
        this.inventoryRepository = inventoryRepository;
    }

    public void cook(Order newOrder) {
        newOrder.make(recipeRepository, inventoryRepository);
    }

    public void checkIfEnoughIngredientsForOrder(Order newOrder) {
        Map<IngredientId, Integer> ingredientsQuantities = newOrder.getAllIngredientsQuantities(recipeRepository);
        ingredientsQuantities.forEach((ingredientId, quantity) -> {
            if (inventoryRepository.findIngredientQuantity(ingredientId) < quantity) {
                throw new InsufficentIngredientsException();
            }
        });
    }
}

