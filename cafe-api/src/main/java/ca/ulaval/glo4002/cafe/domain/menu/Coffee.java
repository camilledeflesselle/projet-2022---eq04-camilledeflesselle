package ca.ulaval.glo4002.cafe.domain.menu;

import ca.ulaval.glo4002.cafe.domain.bill.Amount;
import ca.ulaval.glo4002.cafe.domain.inventory.IInventoryRepository;
import ca.ulaval.glo4002.cafe.domain.recipe.Recipe;

public class Coffee {
    protected String name;
    private final Amount amount;

    public Coffee(String name, Amount amount) {
        this.name = name;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public Amount getPrice() {
        return amount;
    }

    public void makeCoffee(Recipe recipe, IInventoryRepository inventoryRepository) {
        recipe.makeRecipe(inventoryRepository);
    }

}
