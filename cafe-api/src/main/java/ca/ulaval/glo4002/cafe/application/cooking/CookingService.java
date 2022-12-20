package ca.ulaval.glo4002.cafe.application.cooking;

import ca.ulaval.glo4002.cafe.domain.cooking.Cooker;
import ca.ulaval.glo4002.cafe.domain.inventory.IInventoryRepository;
import ca.ulaval.glo4002.cafe.domain.order.Order;
import ca.ulaval.glo4002.cafe.domain.recipe.IRecipeRepository;

public class CookingService {
    private IRecipeRepository recipeRepository;
    private IInventoryRepository inventoryRepository;

    public CookingService(IRecipeRepository recipeRepository, IInventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
        this.recipeRepository = recipeRepository;
    }

    public void cookOrder(Order newOrder) {
        Cooker cooker = new Cooker(recipeRepository, inventoryRepository.getInventory());
        cooker.checkIfEnoughIngredientsForOrder(newOrder);
        cooker.cook(newOrder);
        inventoryRepository.save(cooker.getInventory());
    }
}
