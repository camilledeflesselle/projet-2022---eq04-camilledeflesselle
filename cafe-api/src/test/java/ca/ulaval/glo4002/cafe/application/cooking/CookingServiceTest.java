package ca.ulaval.glo4002.cafe.application.cooking;

import ca.ulaval.glo4002.cafe.application.inventory.InventoryService;
import ca.ulaval.glo4002.cafe.domain.inventory.Ingredient;
import ca.ulaval.glo4002.cafe.domain.menu.MenuItem;
import ca.ulaval.glo4002.cafe.domain.order.Order;
import ca.ulaval.glo4002.cafe.domain.recipe.IRecipeRepository;
import ca.ulaval.glo4002.cafe.domain.recipe.Recipe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;

class CookingServiceTest {
    private static final List<MenuItem> A_EMPTY_MENU_ITEM_LIST = new ArrayList<>();
    private static final String A_RECIPE_NAME = "recipeName";
    private static final String AN_INGREDIENT_NAME = "ingredientName";

    private IRecipeRepository recipeRepository;
    private CookingService cookingService;
    private InventoryService inventoryServiceMock;

    @BeforeEach
    public void setUp() {
        recipeRepository = mock(IRecipeRepository.class);
        inventoryServiceMock = mock(InventoryService.class);
        cookingService = new CookingService(recipeRepository, inventoryServiceMock);
    }

    @Test
    public void givenAnEmptyOrder_whenCookingOrder_thenRecipeRepositoryIsNotCalled() {
        Order order = new Order(A_EMPTY_MENU_ITEM_LIST);

        cookingService.cookOrder(order);

        verify(recipeRepository, never()).findRecipeByName(anyString());
    }

    @Test
    public void givenAnEmptyOrder_whenCookingOrder_thenRemoveIngredientsIsCalledWithEmptyMap() {
        Order order = new Order(A_EMPTY_MENU_ITEM_LIST);

        cookingService.cookOrder(order);

        verify(inventoryServiceMock).removeIngredients(new HashMap<>());
    }

    @Test
    public void givenAnOrderWithOneMenuItem_whenCookingOrder_thenRecipeRepositoryIsCalledWithRecipeName() {
        MenuItem menuItem = mock(MenuItem.class);
        when(menuItem.getName()).thenReturn(A_RECIPE_NAME);
        when(recipeRepository.findRecipeByName(A_RECIPE_NAME)).thenReturn(mock(Recipe.class));
        Order order = new Order(List.of(menuItem));

        cookingService.cookOrder(order);

        verify(recipeRepository).findRecipeByName(A_RECIPE_NAME);
    }

    @Test
    public void givenAnOrderWithOneMenuItem_whenCookingOrder_thenRemoveIngredientsIsCalledWithIngredientsOfTheRecipe() {
        Ingredient ingredient = mock(Ingredient.class);
        when(ingredient.getName()).thenReturn(AN_INGREDIENT_NAME);
        Recipe recipe = mock(Recipe.class);
        when(recipe.getIngredients()).thenReturn(Map.of(ingredient, 1));
        when(recipeRepository.findRecipeByName(anyString())).thenReturn(recipe);
        MenuItem menuItem = mock(MenuItem.class);
        when(menuItem.getName()).thenReturn(A_RECIPE_NAME);
        Order order = new Order(List.of(menuItem));

        cookingService.cookOrder(order);

        verify(inventoryServiceMock).removeIngredients(Map.of(ingredient, 1));
    }
}