package ca.ulaval.glo4002.cafe.infrastructure.persistance.repositories;

import ca.ulaval.glo4002.cafe.application.inventory.IngredientType;
import ca.ulaval.glo4002.cafe.domain.inventory.Ingredient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class InventoryRepositoryInMemoryTest {
    private static final Ingredient AN_EXISTING_INGREDIENT = new Ingredient(IngredientType.CHOCOLATE.getLabel(), 0);

    private InventoryRepositoryInMemory inventoryRepository;

    @BeforeEach
    public void initializeRepository() {
        inventoryRepository = new InventoryRepositoryInMemory();
    }

    @Test
    void whenGetIngredientsNames_thenFindChocolateAndEspressoAndMilkAndWater() {
        //List<String> ingredientNames = inventoryRepository.getIngredientsNames();

        //assertTrue(ingredientNames.containsAll(Arrays.stream(IngredientType.values()).toList()));
    }


    @Test
    void whenFindQuantityOfExistingIngredient_thenReturnsAValue() {
        assertNotNull(inventoryRepository.findIngredientQuantity(IngredientType.CHOCOLATE.getLabel()));
    }

    @Test
    void whenInitialized_thenAllIngredientsInStorageHaveAQuantityOfZero() {
        this.allIngredientsInStorageHaveAQuantityOfZero();
    }


    @Test
    void whenAddIngredientsForIngredientsInStorage_thenTheirQuantitiesAreUpdated() {
        inventoryRepository.save(AN_EXISTING_INGREDIENT);

        assertEquals(AN_EXISTING_INGREDIENT, inventoryRepository.findByName(AN_EXISTING_INGREDIENT.getName()));
    }

    @Test
    void whenRemoveIngredientForIngredientsInStorage_thenTheirQuantitiesIsUpdated() {
        //inventoryRepository.saveIngredients(SOME_INGREDIENTS);
        //inventoryRepository.(SOME_INGREDIENTS);

        //assertEquals(0, inventoryRepository.findIngredientQuantity(AN_EXISTING_INGREDIENT));
        //assertEquals(0, inventoryRepository.findIngredientQuantity(ANOTHER_EXISTING_INGREDIENT));
    }

    @Test
    void givenSomeIngredientsQuantitiesPositive_whenDeleteAll_thenStorageIngredientsContainsNoQuantities() {
        inventoryRepository.save(AN_EXISTING_INGREDIENT);
        inventoryRepository.deleteAll();
        this.allIngredientsInStorageHaveAQuantityOfZero();
    }

    private void allIngredientsInStorageHaveAQuantityOfZero() {
        assertEquals(0, inventoryRepository.findIngredientQuantity(AN_EXISTING_INGREDIENT.getName()));
    }
}