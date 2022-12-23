package ca.ulaval.glo4002.cafe.domain.cooking;

import ca.ulaval.glo4002.cafe.domain.inventory.IngredientId;
import ca.ulaval.glo4002.cafe.domain.inventory.Ingredients;
import ca.ulaval.glo4002.cafe.domain.inventory.Inventory;
import ca.ulaval.glo4002.cafe.domain.order.Order;
import ca.ulaval.glo4002.cafe.domain.recipe.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class CookerTest {
    private static final IngredientId AN_INGREDIENT_ID = new IngredientId("Milk");
    private static final Ingredients NEEDED_QUANTITIES = new Ingredients() {
        {
            addIngredient(AN_INGREDIENT_ID, 10);
        }
    };
    private RecipeRepository recipeRepository;
    private Inventory inventory;
    private Order order;
    private Cooker cooker;

    @BeforeEach
    public void setUp() {
        cooker = new Cooker();
        recipeRepository = mock(RecipeRepository.class);
        order = mock(Order.class);
        inventory = mock(Inventory.class);
    }

    @Test
    public void whenCookOrder_thenOrderMakeWithRecipeRepositoryAndInventory() {
        cooker.cook(order, inventory, recipeRepository);
        verify(order).make(recipeRepository, inventory);
    }

    @Test
    public void whenCheckIfEnoughIngredientsForOrderInInventory_thenOrderGetIngredientsNeededAccordingToRecipeRepository() {
        when(order.getAllIngredientsQuantities(recipeRepository)).thenReturn(NEEDED_QUANTITIES);
        when(inventory.hasMoreIngredients(NEEDED_QUANTITIES)).thenReturn(true);

        cooker.checkIfEnoughIngredients(order, inventory, recipeRepository);

        verify(order).getAllIngredientsQuantities(recipeRepository);
        verify(inventory).hasMoreIngredients(NEEDED_QUANTITIES);
    }

    @Test
    public void givenNotEnoughIngredients_whenCheckIfEnoughIngredientsForOrderInInventory_thenRaiseInsufficientIngredientException() {
        when(order.getAllIngredientsQuantities(recipeRepository)).thenReturn(NEEDED_QUANTITIES);
        when(inventory.hasMoreIngredients(NEEDED_QUANTITIES)).thenReturn(false);
        assertThrows(InsufficentIngredientsException.class, () -> cooker.checkIfEnoughIngredients(order, inventory, recipeRepository));
    }
}