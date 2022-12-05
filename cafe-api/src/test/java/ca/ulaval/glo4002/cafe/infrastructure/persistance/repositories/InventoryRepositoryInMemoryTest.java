package ca.ulaval.glo4002.cafe.infrastructure.persistance.repositories;

import ca.ulaval.glo4002.cafe.application.inventory.IngredientType;
import ca.ulaval.glo4002.cafe.domain.inventory.Ingredient;
import ca.ulaval.glo4002.cafe.domain.inventory.IngredientId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InventoryRepositoryInMemoryTest {
    private static final IngredientId AN_EXISTING_INGREDIENT_ID = new IngredientId(IngredientType.CHOCOLATE.getLabel());
    private static final Ingredient AN_EXISTING_INGREDIENT = new Ingredient(AN_EXISTING_INGREDIENT_ID, 0);

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
        assertNotNull(inventoryRepository.findIngredientQuantity(AN_EXISTING_INGREDIENT_ID));
    }

    @Test
    void whenInitialized_thenAllIngredientsInStorageHaveAQuantityOfZero() {
        this.allIngredientsInStorageHaveAQuantityOfZero();
    }


    @Test
    void whenAddIngredientsForIngredientsInStorage_thenTheirQuantitiesAreUpdated() {
        inventoryRepository.save(AN_EXISTING_INGREDIENT);

        assertEquals(AN_EXISTING_INGREDIENT, inventoryRepository.findByName(AN_EXISTING_INGREDIENT_ID));
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
        assertEquals(0, inventoryRepository.findIngredientQuantity(AN_EXISTING_INGREDIENT_ID));
    }
}