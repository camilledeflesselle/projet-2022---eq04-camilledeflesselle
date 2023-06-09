package ca.ulaval.glo4002.cafe.application.cooking;

import ca.ulaval.glo4002.cafe.domain.cooking.Cooker;
import ca.ulaval.glo4002.cafe.domain.inventory.Inventory;
import ca.ulaval.glo4002.cafe.domain.inventory.InventoryRepository;
import ca.ulaval.glo4002.cafe.domain.order.Order;
import ca.ulaval.glo4002.cafe.domain.recipe.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;


class CookingServiceTest {
    private RecipeRepository recipeRepository;
    private InventoryRepository inventoryRepository;
    private Cooker cooker;
    private Inventory inventory;
    private CookingService cookingService;

    @BeforeEach
    public void setUp() {
        recipeRepository = mock(RecipeRepository.class);
        inventoryRepository = mock(InventoryRepository.class);
        inventory = mock(Inventory.class);
        when(inventoryRepository.getInventory()).thenReturn(inventory);
        cooker = mock(Cooker.class);
        cookingService = new CookingService(recipeRepository, inventoryRepository, cooker);
    }

    @Test
    public void whenCookingOrder_thenGetInventoryFromRepository() {
        Order order = mock(Order.class);
        cookingService.cookOrder(order);
        verify(inventoryRepository).getInventory();
    }

    @Test
    public void givenInventoryAndOrder_whenCookOrder_thenCookerCheckIfThereIsEnoughIngredientsInStorage(){
        Order order = mock(Order.class);
        cookingService.cookOrder(order);
        verify(cooker).checkIfEnoughIngredients(order, inventoryRepository.getInventory(), recipeRepository);
    }

    @Test
    public void givenInventoryAndOrder_whenCookOrder_thenCookerCookOrder(){
        Order order = mock(Order.class);
        cookingService.cookOrder(order);
        verify(cooker).cook(order, inventory, recipeRepository);
    }
}