package ca.ulaval.glo4002.cafe.domain.menu;

import ca.ulaval.glo4002.cafe.domain.bill.Amount;
import ca.ulaval.glo4002.cafe.domain.inventory.IInventoryRepository;
import ca.ulaval.glo4002.cafe.domain.recipe.IRecipeRepository;
import ca.ulaval.glo4002.cafe.domain.recipe.Recipe;

public class MenuItem {
    private final String name;
    private final Amount amount;

    public MenuItem(String name, Amount amount) {
        this.name = name;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public Amount getPrice() {
        return amount;
    }

    public void cook(IRecipeRepository recipeRepository, IInventoryRepository inventoryRepository) {
        Recipe recipe = recipeRepository.findByName(this.name);
        recipe.makeRecipe(inventoryRepository);
    }
}
