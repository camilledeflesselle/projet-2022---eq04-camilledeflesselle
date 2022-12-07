package ca.ulaval.glo4002.cafe.application.cooking;

import ca.ulaval.glo4002.cafe.domain.cooking.Cooker;
import ca.ulaval.glo4002.cafe.domain.inventory.IInventoryRepository;
import ca.ulaval.glo4002.cafe.domain.order.Order;
import ca.ulaval.glo4002.cafe.domain.recipe.IRecipeRepository;

public class CookingService {
    private final Cooker cooker;

    public CookingService(IRecipeRepository recipeRepository, IInventoryRepository inventoryRepository) {
        this.cooker = new Cooker(recipeRepository, inventoryRepository);
    }

    public void cookOrder(Order newOrder) {
        this.cooker.checkIfEnoughIngredientsForOrder(newOrder);
        this.cooker.cook(newOrder);
    }
}
