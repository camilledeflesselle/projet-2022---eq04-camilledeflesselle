package ca.ulaval.glo4002.cafe.application.cooking;

import ca.ulaval.glo4002.cafe.application.inventory.InventoryService;
import ca.ulaval.glo4002.cafe.domain.inventory.Ingredient;
import ca.ulaval.glo4002.cafe.domain.menu.MenuItem;
import ca.ulaval.glo4002.cafe.domain.order.Order;
import ca.ulaval.glo4002.cafe.domain.recipe.IRecipeRepository;
import ca.ulaval.glo4002.cafe.domain.recipe.Recipe;

import java.util.HashMap;
import java.util.Map;

public class CookingService {
    private final IRecipeRepository recipeRepository;
    private final InventoryService inventoryService;

    public CookingService(IRecipeRepository recipeRepository, InventoryService inventoryService) {
        this.recipeRepository = recipeRepository;
        this.inventoryService = inventoryService;
    }

    private static void updateIngredientsQuantityForRecipe(Map<Ingredient, Integer> ingredients, Recipe recipe) {
        for (Map.Entry<Ingredient, Integer> entry : recipe.getIngredients().entrySet()) {
            Ingredient ingredient = entry.getKey();
            Integer quantity = entry.getValue();
            ingredients.put(ingredient, ingredients.getOrDefault(ingredient, 0) + quantity);
        }
    }

    public void cookOrder(Order newOrder) {
        Map<Ingredient, Integer> ingredientsNeeded = new HashMap<>();

        for (MenuItem item : newOrder.getMenuItems()) {
            String recipeName = item.getName();
            Recipe recipe = this.recipeRepository.findRecipeByName(recipeName);
            updateIngredientsQuantityForRecipe(ingredientsNeeded, recipe);
        }
        this.inventoryService.removeIngredients(ingredientsNeeded);
    }
}
