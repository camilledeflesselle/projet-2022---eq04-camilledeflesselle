package ca.ulaval.glo4002.cafe.infrastructure.persistance.repositories;

import ca.ulaval.glo4002.cafe.domain.inventory.Ingredient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class InventoryRepositoryInMemoryTest {
    private static final String AN_EXISTING_INGREDIENT_NAME = "Chocolate";
    private static final Ingredient AN_EXISTING_INGREDIENT = new Ingredient(AN_EXISTING_INGREDIENT_NAME);
    private static final Ingredient ANOTHER_EXISTING_INGREDIENT = new Ingredient("Water");

    private InventoryRepositoryInMemory inventoryRepository;
    private static final Map<Ingredient, Integer> SOME_INGREDIENTS = new HashMap<>()  {
        {
        put(AN_EXISTING_INGREDIENT, 500);
        put(ANOTHER_EXISTING_INGREDIENT, 200);
        }
    };

    @BeforeEach
    public void initializeRepository() {
        inventoryRepository = new InventoryRepositoryInMemory();
    }

    @Test
    void whenGetIngredientsNames_thenFindChocolateAndEspressoAndMilkAndWater() {
        List<String> ingredientNames = inventoryRepository.getIngredientsNames();

        assertTrue(ingredientNames.containsAll(List.of("Chocolate", "Espresso", "Milk", "Water")));
    }


    @Test
    void whenFindQuantityOfExistingIngredient_thenReturnsAValue() {
        assertNotNull(inventoryRepository.findIngredientQuantity(AN_EXISTING_INGREDIENT));
    }

    @Test
    void whenInitialized_thenAllIngredientsInStorageHaveAQuantityOfZero() {
        this.allIngredientsInStorageHaveAQuantityOfZero();
    }


    @Test
    void whenAddIngredientsForIngredientsInStorage_thenTheirQuantitiesAreUpdated() {
        inventoryRepository.saveIngredients(SOME_INGREDIENTS);

        assertEquals(SOME_INGREDIENTS.get(AN_EXISTING_INGREDIENT), inventoryRepository.findIngredientQuantity(AN_EXISTING_INGREDIENT));
        assertEquals(SOME_INGREDIENTS.get(ANOTHER_EXISTING_INGREDIENT), inventoryRepository.findIngredientQuantity(ANOTHER_EXISTING_INGREDIENT));
    }

    @Test
    void whenRemoveIngredientsForIngredientsInStorage_thenTheirQuantitiesAreUpdated() {
        inventoryRepository.saveIngredients(SOME_INGREDIENTS);
        inventoryRepository.removeIngredients(SOME_INGREDIENTS);

        assertEquals(0, inventoryRepository.findIngredientQuantity(AN_EXISTING_INGREDIENT));
        assertEquals(0, inventoryRepository.findIngredientQuantity(ANOTHER_EXISTING_INGREDIENT));
    }

    @Test
    void givenSomeIngredientsQuantitiesPositive_whenDeleteAll_thenStorageIngredientsContainsNoQuantities() {
        inventoryRepository.saveIngredients(SOME_INGREDIENTS);
        inventoryRepository.deleteAll();
        this.allIngredientsInStorageHaveAQuantityOfZero();
    }

    private void allIngredientsInStorageHaveAQuantityOfZero() {
        assertEquals(0, inventoryRepository.findIngredientQuantity(new Ingredient("Chocolate")));
        assertEquals(0, inventoryRepository.findIngredientQuantity(new Ingredient("Espresso")));
        assertEquals(0, inventoryRepository.findIngredientQuantity(new Ingredient("Milk")));
        assertEquals(0, inventoryRepository.findIngredientQuantity(new Ingredient("Water")));
    }
}