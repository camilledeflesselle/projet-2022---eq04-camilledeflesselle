package ca.ulaval.glo4002.cafe.domain.recipe;

import ca.ulaval.glo4002.cafe.domain.inventory.Ingredients;
import ca.ulaval.glo4002.cafe.domain.inventory.Inventory;
import ca.ulaval.glo4002.cafe.domain.menu.MenuItemId;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class RecipeTest {
    private static final Ingredients INGREDIENTS = mock(Ingredients.class);
    private Recipe recipe;

    @Test
    void whenCookWithInventory_thenRemoveHisIngredientFromInventory() {
        Inventory inventory = mock(Inventory.class);
        recipe = givenRecipeWithIngredient();
        recipe.cookWith(inventory);
        verify(inventory).removeIngredients(INGREDIENTS);
    }

    private Recipe givenRecipeWithIngredient() {
        recipe = new Recipe(new MenuItemId("Cappuccino"), INGREDIENTS);
        return recipe;
    }
}