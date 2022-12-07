package ca.ulaval.glo4002.cafe.domain.menu;

import ca.ulaval.glo4002.cafe.domain.bill.Amount;
import ca.ulaval.glo4002.cafe.domain.inventory.IInventoryRepository;
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

    public void cook(Recipe recipe, IInventoryRepository inventoryRepository) {
        recipe.cookWithStorageIn(inventoryRepository);
    }

    public String getName() {
        return this.id.getName();
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof MenuItem menuItem)) return false;
        return this.id.equals(menuItem.id);
    }

    @Override
    public int hashCode() {
        return this.id.hashCode();
    }

}