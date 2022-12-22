package ca.ulaval.glo4002.cafe.domain.order;

import ca.ulaval.glo4002.cafe.domain.bill.Amount;
import ca.ulaval.glo4002.cafe.domain.inventory.Ingredient;
import ca.ulaval.glo4002.cafe.domain.inventory.IngredientId;
import ca.ulaval.glo4002.cafe.domain.inventory.Inventory;
import ca.ulaval.glo4002.cafe.domain.menu.MenuItem;
import ca.ulaval.glo4002.cafe.domain.menu.MenuItemId;
import ca.ulaval.glo4002.cafe.domain.recipe.Recipe;
import ca.ulaval.glo4002.cafe.domain.recipe.RecipeRepository;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class OrderTest {
    private static final String AN_ITEM_NAME = "cafe";
    private static final String ANOTHER_ITEM_NAME = "big10";
    private static final List<MenuItem> A_EMPTY_MENU_ITEM_LIST = new ArrayList<>();
    private static final MenuItemId A_MENU_ITEM_ID = new MenuItemId(AN_ITEM_NAME);
    private static final MenuItemId ANOTHER_MENU_ITEM_ID = new MenuItemId(ANOTHER_ITEM_NAME);
    private static final IngredientId AN_INGREDIENT_ID = new IngredientId("ingredient");
    private Order order;

    @Test
    public void whenAppendingItemsFromOrder_thenItemsAreAddedInTheOrderThatTheyAppearInTheOrder() {
        order = new Order(new ArrayList<>());
        MenuItem anItem = mock(MenuItem.class);
        MenuItem anotherItem = mock(MenuItem.class);
        List<MenuItem> menuItems = new ArrayList<>(List.of(anItem, anotherItem));

        Order newOrder = new Order(menuItems);
        order.appendMenuItemsFrom(newOrder);

        assertEquals(menuItems, order.getMenuItems());
    }

    @Test
    public void whenRequestingItemsStrFromOrder_thenReturnOrderedListOfMenuItemsStr() {
        MenuItem anItem = mock(MenuItem.class);
        MenuItem anotherItem = mock(MenuItem.class);
        when(anItem.getName()).thenReturn(AN_ITEM_NAME);
        when(anotherItem.getName()).thenReturn((ANOTHER_ITEM_NAME));
        List<MenuItem> menuItems = new ArrayList<>(List.of(anItem, anotherItem));
        order = new Order(menuItems);

        List<String> returnedList = order.getListOfMenuItemNames();
        List<String> expectedList = new ArrayList<>(List.of(AN_ITEM_NAME, ANOTHER_ITEM_NAME));

        assertEquals(expectedList, returnedList);
    }

    @Test
    public void givenAnEmptyOrder_whenMakeOrder_thenRecipeRepositoryIsNotCalled() {
        order = new Order(A_EMPTY_MENU_ITEM_LIST);
        Inventory inventory = mock(Inventory.class);
        RecipeRepository recipeRepository = mock(RecipeRepository.class);

        order.make(recipeRepository, inventory);

        verify(recipeRepository, never()).findById(any());
    }


    @Test
    public void givenMenuItemsInOrder_whenMakeOrder_thenRecipeOfEveryMenuItemCookWithInventory() {
        Inventory inventory = mock(Inventory.class);
        RecipeRepository recipeRepository = mock(RecipeRepository.class);
        Recipe recipe = mock(Recipe.class);
        MenuItem anItem = mock(MenuItem.class);
        MenuItem anotherItem = mock(MenuItem.class);
        when(anItem.getId()).thenReturn(new MenuItemId(AN_ITEM_NAME));
        when(anotherItem.getId()).thenReturn(new MenuItemId(ANOTHER_ITEM_NAME));
        when(recipeRepository.findById(new MenuItemId(AN_ITEM_NAME))).thenReturn(recipe);
        when(recipeRepository.findById(new MenuItemId(ANOTHER_ITEM_NAME))).thenReturn(recipe);
        order = new Order(List.of(anItem, anotherItem));

        order.make(recipeRepository, inventory);

        verify(recipe, times(2)).cookWith(inventory);
    }

    @Test
    public void whenCalculateTotalPrice_thenTotalPriceIsSumOfAllMenuItemsPrice() {
        MenuItem anItem = mock(MenuItem.class);
        MenuItem anotherItem = mock(MenuItem.class);
        when(anItem.getPrice()).thenReturn(new Amount(1f));
        when(anotherItem.getPrice()).thenReturn(new Amount(2f));
        order = new Order(List.of(anItem, anotherItem));

        Amount totalPrice = order.calculateTotal();

        assertEquals(new Amount(3f), totalPrice);
    }

    @Test
    public void whenGetMenuItems_thenReturnsMenuItems() {
        MenuItem anItem = mock(MenuItem.class);
        MenuItem anotherItem = mock(MenuItem.class);
        order = new Order(List.of(anItem, anotherItem));

        List<MenuItem> returnedList = order.getMenuItems();
        List<MenuItem> expectedList = new ArrayList<>(List.of(anItem, anotherItem));

        assertEquals(expectedList, returnedList);
    }

    @Test
    public void whenGetAllIngredientsQuantities_thenReturnsSumOfAllMenuItemsIngredientsQuantities() {
        RecipeRepository recipeRepository = mock(RecipeRepository.class);
        given2RecipesInRepositoryWithSameIngredientAndDifferentQuantities(recipeRepository, 1, 3, AN_INGREDIENT_ID);
        order = givenOrderWith2MenuItems();

        Map<IngredientId, Integer> returnedMap = order.getAllIngredientsQuantities(recipeRepository);

        assertEquals(4, returnedMap.get(AN_INGREDIENT_ID));
    }

    private Order givenOrderWith2MenuItems() {
        MenuItem anItem = mock(MenuItem.class);
        MenuItem anotherItem = mock(MenuItem.class);
        when(anItem.getId()).thenReturn(A_MENU_ITEM_ID);
        when(anotherItem.getId()).thenReturn(ANOTHER_MENU_ITEM_ID);
        return new Order(List.of(anItem, anotherItem));
    }

    private void given2RecipesInRepositoryWithSameIngredientAndDifferentQuantities(RecipeRepository recipeRepository, int quantity1, int quantity2, IngredientId ingredientId) {
        Recipe recipe1 = new Recipe(A_MENU_ITEM_ID,List.of(new Ingredient(ingredientId, quantity1)));
        Recipe recipe2 = new Recipe(ANOTHER_MENU_ITEM_ID,List.of(new Ingredient(ingredientId, quantity2)));
        when(recipeRepository.findById(A_MENU_ITEM_ID)).thenReturn(recipe1);
        when(recipeRepository.findById(ANOTHER_MENU_ITEM_ID)).thenReturn(recipe2);
    }
}
