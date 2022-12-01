package ca.ulaval.glo4002.cafe.application.cooking;

import ca.ulaval.glo4002.cafe.domain.inventory.IInventoryRepository;
import ca.ulaval.glo4002.cafe.domain.order.Order;
import ca.ulaval.glo4002.cafe.domain.recipe.IRecipeRepository;


public class CookingService {
    private final IRecipeRepository recipeRepository;
    private final IInventoryRepository inventoryRepository;

    public CookingService(IRecipeRepository recipeRepository, IInventoryRepository inventoryRepository) {
        this.recipeRepository = recipeRepository;
        this.inventoryRepository = inventoryRepository;
    }

    public void cookOrder(Order newOrder) {
        newOrder.make(this.recipeRepository, this.inventoryRepository);
    }
}
