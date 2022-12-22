package ca.ulaval.glo4002.cafe.application.cooking;

import ca.ulaval.glo4002.cafe.domain.cooking.Cooker;
import ca.ulaval.glo4002.cafe.domain.inventory.Inventory;
import ca.ulaval.glo4002.cafe.domain.inventory.InventoryRepository;
import ca.ulaval.glo4002.cafe.domain.order.Order;
import ca.ulaval.glo4002.cafe.domain.recipe.RecipeRepository;

public class CookingService {
    private final RecipeRepository recipeRepository;
    private Cooker cooker;
    private final InventoryRepository inventoryRepository;

    public CookingService(RecipeRepository recipeRepository, InventoryRepository inventoryRepository, Cooker cooker) {
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
