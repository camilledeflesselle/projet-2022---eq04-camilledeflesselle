package ca.ulaval.glo4002.cafe.domain.order;

import ca.ulaval.glo4002.cafe.domain.bill.Amount;
import ca.ulaval.glo4002.cafe.domain.inventory.IInventoryRepository;
import ca.ulaval.glo4002.cafe.domain.inventory.IngredientId;
import ca.ulaval.glo4002.cafe.domain.menu.MenuItem;
import ca.ulaval.glo4002.cafe.domain.recipe.IRecipeRepository;
import ca.ulaval.glo4002.cafe.domain.recipe.Recipe;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Order {
    private List<MenuItem> menuItems;

    public Order(List<MenuItem> menuItems) {
        this.menuItems = menuItems;
    }

    public List<MenuItem> getMenuItems() {
        return menuItems;
    }

    public List<String> getListOfOrderedItemsStr() {
        return menuItems.stream().map(MenuItem::getName).toList();
    }

    public Order appendMenuItemsFrom(Order order) {
        this.menuItems.addAll(order.getMenuItems());
        return this;
    }

    public void make(IRecipeRepository recipeRepository, IInventoryRepository inventoryRepository) {
        menuItems.forEach(menuItem -> {
            Recipe recipe = recipeRepository.findById(menuItem.getId());
            menuItem.cook(recipe, inventoryRepository);
        });
    }

    public Map<IngredientId, Integer> getAllIngredientsQuantities(IRecipeRepository recipeRepository) {
        Map<IngredientId, Integer> ingredients = new HashMap<>();
        menuItems.forEach(menuItem -> {
            Recipe recipe = recipeRepository.findById(menuItem.getId());
            recipe.getIngredients().forEach(ingredient -> {
                if (ingredients.containsKey(ingredient.getId())) {
                    ingredients.put(ingredient.getId(), ingredients.get(ingredient.getId()) + ingredient.getQuantity());
                } else {
                    ingredients.put(ingredient.getId(), ingredient.getQuantity());
                }
            });
        });
        return ingredients;
    }

    public Amount calculateTotal(Amount subtotal) {
        return menuItems.stream().map(MenuItem::getPrice).reduce(subtotal, Amount::add);
    }
}
