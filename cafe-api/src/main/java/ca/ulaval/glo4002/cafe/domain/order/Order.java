package ca.ulaval.glo4002.cafe.domain.order;

import ca.ulaval.glo4002.cafe.domain.inventory.IInventoryRepository;
import ca.ulaval.glo4002.cafe.domain.menu.MenuItem;
import ca.ulaval.glo4002.cafe.domain.recipe.IRecipeRepository;

import java.util.List;

public class Order {
    private final List<MenuItem> menuItems;

    public Order(List<MenuItem> menuItems) {
        this.menuItems = menuItems;
    }

    public List<MenuItem> getMenuItems() {
        return menuItems;
    }

    public List<String> getListOfOrderedItemsStr() {
        return menuItems.stream().map(MenuItem::getName).toList();
    }

    public void appendMenuItemsFrom(Order order) {
        this.menuItems.addAll(order.getMenuItems());
    }

    public void make(IRecipeRepository recipeRepository, IInventoryRepository inventoryRepository) {
        menuItems.forEach(menuItem -> menuItem.cook(recipeRepository, inventoryRepository));
    }
}
