package ca.ulaval.glo4002.cafe.application.inventory;

import ca.ulaval.glo4002.cafe.domain.cooking.InsufficentIngredientsException;
import ca.ulaval.glo4002.cafe.domain.inventory.IInventoryRepository;
import ca.ulaval.glo4002.cafe.domain.inventory.Ingredient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InventoryServiceTest {
    private IInventoryRepository inventoryRepositoryMock;
    private static final String AN_INGREDIENT_NAME = "ingredientName";
    private InventoryService inventoryService;

    @BeforeEach
    void before() {
        inventoryRepositoryMock = mock(IInventoryRepository.class);
        inventoryService = new InventoryService(inventoryRepositoryMock);
    }

    @Test
    void givenAListOfIngredients_whenAddingIngredientsInInventory_thenRepositoryIsCalledWithIngredients() {
        Map<Ingredient, Integer> ingredients = new HashMap<>();
        ingredients.put(new Ingredient(AN_INGREDIENT_NAME), 1);

        inventoryService.addIngredientsInInventory(ingredients);

        verify(inventoryRepositoryMock).saveIngredients(ingredients);
    }

    @Test
    void whenReset_thenIngredientsAreDeletedFromRepository() {
        inventoryService.reset();

        verify(inventoryRepositoryMock).deleteAll();
    }

    @Test
    void givenAListOfIngredients_whenCheckingIfEnoughIngredients_thenRepositoryIsCalledWithIngredients() {
        Map<Ingredient, Integer> ingredients = new HashMap<>();
        Ingredient ingredient = new Ingredient(AN_INGREDIENT_NAME);
        ingredients.put(ingredient, 1);

        inventoryService.isEnoughIngredients(ingredients);

        verify(inventoryRepositoryMock).findIngredientQuantity(ingredient);
    }

    @Test
    void givenEnoughIngredientInRepository_whenCheckingIfEnoughIngredients_shouldReturnTrue() {
        Map<Ingredient, Integer> ingredients = new HashMap<>();
        Ingredient ingredient = new Ingredient(AN_INGREDIENT_NAME);
        ingredients.put(ingredient, 1);
        when(inventoryRepositoryMock.findIngredientQuantity(ingredient)).thenReturn(1);

        boolean isEnoughIngredients = inventoryService.isEnoughIngredients(ingredients);

        assertTrue(isEnoughIngredients);
    }

    @Test
    void givenNotEnoughIngredientInRepository_whenCheckingIfEnoughIngredients_shouldReturnFalse() {
        Map<Ingredient, Integer> ingredients = new HashMap<>();
        Ingredient ingredient = new Ingredient(AN_INGREDIENT_NAME);
        ingredients.put(ingredient, 1);
        when(inventoryRepositoryMock.findIngredientQuantity(ingredient)).thenReturn(0);

        boolean isEnoughIngredients = inventoryService.isEnoughIngredients(ingredients);

        assertFalse(isEnoughIngredients);
    }

    @Test
    void givenNotEnoughIngredientForOnlyOneInRepository_whenCheckingIfEnoughIngredients_shouldReturnFalse() {
        Map<Ingredient, Integer> ingredients = new HashMap<>();
        Ingredient ingredient1 = new Ingredient("ingredientName1");
        Ingredient ingredient2 = new Ingredient("ingredientName2");
        ingredients.put(ingredient1, 1);
        ingredients.put(ingredient2, 1);
        when(inventoryRepositoryMock.findIngredientQuantity(ingredient1)).thenReturn(1);
        when(inventoryRepositoryMock.findIngredientQuantity(ingredient2)).thenReturn(0);

        boolean isEnoughIngredients = inventoryService.isEnoughIngredients(ingredients);

        assertFalse(isEnoughIngredients);
    }

    @Test
    void givenAListOfIngredientsAndEnoughQuantityInInventory_whenRemovingIngredients_thenRemoveIngredientFromTheRepository() {
        Map<Ingredient, Integer> ingredients = new HashMap<>();
        Ingredient ingredient = new Ingredient(AN_INGREDIENT_NAME);
        ingredients.put(ingredient, 1);
        when(inventoryRepositoryMock.findIngredientQuantity(ingredient)).thenReturn(1);

        inventoryService.removeIngredients(ingredients);

        verify(inventoryRepositoryMock).removeIngredients(ingredients);
    }

    @Test
    void givenAListOfIngredients_whenRemovingIngredientsAndNotEnoughIngredients_thenExceptionIsThrown() {
        Map<Ingredient, Integer> ingredients = new HashMap<>();
        Ingredient ingredient = new Ingredient(AN_INGREDIENT_NAME);
        ingredients.put(ingredient, 1);
        when(inventoryRepositoryMock.findIngredientQuantity(ingredient)).thenReturn(0);

        assertThrows(InsufficentIngredientsException.class,
            () -> inventoryService.removeIngredients(ingredients));
    }

    @Test
    void givenAnInventory_whenGetInventoryStringify_thenRepositoryIsCalled() {
        Map<Ingredient, Integer> ingredients = new HashMap<>();
        String ingredientName1 = "ingredientName1";
        String ingredientName2 = "ingredientName2";
        Ingredient ingredient1 = new Ingredient(ingredientName1);
        Ingredient ingredient2 = new Ingredient(ingredientName2);
        ingredients.put(ingredient1, 1);
        ingredients.put(ingredient2, 2);
        when(inventoryRepositoryMock.getInventory()).thenReturn(ingredients);

        Map<String, Integer> inventoryStringify = inventoryService.getInventoryStringify();

        assertEquals(2, inventoryStringify.size());
        assertEquals(1, inventoryStringify.get(ingredientName1));
        assertEquals(2, inventoryStringify.get(ingredientName2));

    }
}