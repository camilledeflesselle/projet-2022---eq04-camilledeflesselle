package ca.ulaval.glo4002.cafe.domain.order;

import ca.ulaval.glo4002.cafe.domain.bill.Amount;
import ca.ulaval.glo4002.cafe.domain.inventory.Ingredients;
import ca.ulaval.glo4002.cafe.domain.inventory.Inventory;
import ca.ulaval.glo4002.cafe.domain.menu.MenuItem;
import ca.ulaval.glo4002.cafe.domain.recipe.Recipe;
import ca.ulaval.glo4002.cafe.domain.recipe.RecipeRepository;

import java.util.List;

public class Order {
    private final List<MenuItem> menuItems;

    public Order(List<MenuItem> menuItems) {
        this.menuItems = menuItems;
    }

    public List<MenuItem> getMenuItems() {
        return menuItems;
    }

    public List<String> getListOfMenuItemNames() {
        return menuItems.stream().map(MenuItem::getName).toList();
    }

    public Order appendMenuItemsFrom(Order order) {
        this.menuItems.addAll(order.getMenuItems());
        return this;
    }

    public void make(RecipeRepository recipeRepository, Inventory inventory) {
        menuItems.forEach(menuItem -> {
            Recipe recipe = recipeRepository.findById(menuItem.getId());
            recipe.cookWith(inventory);
        });
    }

    public Ingredients getAllIngredientsQuantities(RecipeRepository recipeRepository) {
        Ingredients ingredients = new Ingredients();

        menuItems.forEach(menuItem -> {
            Recipe recipe = recipeRepository.findById(menuItem.getId());
            ingredients.addIngredientsQuantitiesFrom(recipe.getIngredients());
        });
        return ingredients;
    }

    public Amount calculateTotal() {
        return menuItems.stream().map(MenuItem::getPrice).reduce(Amount::add).orElse(new Amount(0));
    }
}
