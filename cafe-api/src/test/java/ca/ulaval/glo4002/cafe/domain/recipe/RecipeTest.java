package ca.ulaval.glo4002.cafe.domain.recipe;

import ca.ulaval.glo4002.cafe.domain.inventory.Ingredient;
import ca.ulaval.glo4002.cafe.domain.inventory.IngredientId;
import ca.ulaval.glo4002.cafe.domain.inventory.Inventory;
import ca.ulaval.glo4002.cafe.domain.menu.MenuItemId;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class RecipeTest {

    private static final Ingredient INGREDIENT = new Ingredient(new IngredientId("ingredient"), 1);
    private Recipe recipe;
    private Inventory inventory;

    @Test
    void whenCookWithInventory_thenRemoveHisIngredientFromInventory() {
        inventory = mock(Inventory.class);
        recipe = givenRecipeWithIngredient();
        recipe.cookWith(inventory);
        verify(inventory).useIngredient(INGREDIENT);
    }

    private Recipe givenRecipeWithIngredient() {
        recipe = new Recipe(new MenuItemId("Cappuccino"), List.of(INGREDIENT));
        return recipe;
    }
}