package ca.ulaval.glo4002.cafe.application.cooking;

import ca.ulaval.glo4002.cafe.domain.cooking.Cooker;
import ca.ulaval.glo4002.cafe.domain.inventory.IInventoryRepository;
import ca.ulaval.glo4002.cafe.domain.inventory.Inventory;
import ca.ulaval.glo4002.cafe.domain.order.Order;
import ca.ulaval.glo4002.cafe.domain.recipe.IRecipeRepository;

public class CookingService {
    private final IRecipeRepository recipeRepository;
    private Cooker cooker;
    private final IInventoryRepository inventoryRepository;

    public CookingService(IRecipeRepository recipeRepository, IInventoryRepository inventoryRepository, Cooker cooker) {
        this.inventoryRepository = inventoryRepository;
        this.recipeRepository = recipeRepository;
        this.cooker = cooker;
    }

    public void cookOrder(Order newOrder) {
        Inventory inventory = this.inventoryRepository.getInventory();
        cooker.checkIfEnoughIngredients(newOrder, inventory, recipeRepository);
        cooker.cook(newOrder, inventory, recipeRepository);
        inventoryRepository.save(inventory);
    }
}
