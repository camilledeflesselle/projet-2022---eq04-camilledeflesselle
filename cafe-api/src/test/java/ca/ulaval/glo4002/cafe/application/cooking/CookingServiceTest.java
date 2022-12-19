package ca.ulaval.glo4002.cafe.application.cooking;

import ca.ulaval.glo4002.cafe.domain.inventory.IIngredientRepository;
import ca.ulaval.glo4002.cafe.domain.order.Order;
import ca.ulaval.glo4002.cafe.domain.recipe.IRecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;


class CookingServiceTest {
    private IRecipeRepository recipeRepository;
    private IIngredientRepository inventoryRepository;
    private CookingService cookingService;

    @BeforeEach
    public void setUp() {
        recipeRepository = mock(IRecipeRepository.class);
        inventoryRepository = mock(IIngredientRepository.class);
        cookingService = new CookingService(recipeRepository, inventoryRepository);
    }

    @Test
    public void whenCookOrder_thenOrderIsCookedWithStorage() {
        Order order = mock(Order.class);
        cookingService.cookOrder(order);
        verify(order).make(recipeRepository, inventoryRepository);
    }
    /* à changer
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

        //verify(inventoryServiceMock).removeIngredients(new HashMap<>());
    }

     à changer
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
    }*/
}