package ca.ulaval.glo4002.cafe.domain.cooking;

import ca.ulaval.glo4002.cafe.domain.inventory.IngredientId;
import ca.ulaval.glo4002.cafe.domain.inventory.Inventory;
import ca.ulaval.glo4002.cafe.domain.order.Order;
import ca.ulaval.glo4002.cafe.domain.recipe.IRecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class CookerTest {
    private static final Map<IngredientId, Integer> NEEDED_QUANTITIES = Map.of(new IngredientId("An ingredient"), 1);
    private static final Integer NULL_INVENTORY_QUANTITY = 0;
    private IRecipeRepository recipeRepository;
    private Inventory inventory;
    private Order order;
    private Cooker cooker;

    @BeforeEach
    public void setUp() {
        cooker = new Cooker();
        recipeRepository = mock(IRecipeRepository.class);
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
        cooker.checkIfEnoughIngredients(order, inventory, recipeRepository);
        verify(order).getAllIngredientsQuantities(recipeRepository);
    }

    @Test
    public void givenNotEnoughIngredients_whenCheckIfEnoughIngredientsForOrderInInventory_thenRaiseInsufficientIngredientException() {
        when(order.getAllIngredientsQuantities(recipeRepository)).thenReturn(NEEDED_QUANTITIES);
        when(inventory.findIngredientQuantity(new IngredientId("An ingredient"))).thenReturn(NULL_INVENTORY_QUANTITY);
        assertThrows(InsufficentIngredientsException.class, () -> cooker.checkIfEnoughIngredients(order, inventory, recipeRepository));
    }
}