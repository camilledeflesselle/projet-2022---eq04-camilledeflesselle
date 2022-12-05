package ca.ulaval.glo4002.cafe.domain.menu;

import ca.ulaval.glo4002.cafe.domain.bill.Amount;
import ca.ulaval.glo4002.cafe.domain.inventory.IInventoryRepository;
import ca.ulaval.glo4002.cafe.domain.recipe.IRecipeRepository;
import ca.ulaval.glo4002.cafe.domain.recipe.Recipe;

public class MenuItem {
    private final MenuItemId id;
    private final Amount amount;

    public MenuItem(MenuItemId id, Amount amount) {
        this.id = id;
        this.amount = amount;
    }

    public MenuItemId getId() {
        return this.id;
    }

    public Amount getPrice() {
        return this.amount;
    }

    public void cook(IRecipeRepository recipeRepository, IInventoryRepository inventoryRepository) {
        Recipe recipe = recipeRepository.findById(this.id);
        recipe.makeRecipe(inventoryRepository);
    }

    public String getName() {
        return this.id.getName();
    }
}
