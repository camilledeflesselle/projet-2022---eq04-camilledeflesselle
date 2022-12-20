package ca.ulaval.glo4002.cafe.domain.inventory;

import ca.ulaval.glo4002.cafe.application.inventory.IngredientType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InventoryTest {
    private static final IngredientId AN_EXISTING_INGREDIENT_ID = new IngredientId(IngredientType.CHOCOLATE.getLabel());
    private static final Ingredient AN_EXISTING_INGREDIENT = new Ingredient(AN_EXISTING_INGREDIENT_ID, 0);

    private Inventory inventory;

    @BeforeEach
    public void initializeInventory() {
        inventory = new Inventory();
        givenIngredientsInInventory();
    }


    @Test
    void whenInitialized_thenAllIngredientsInStorageHaveAQuantityOfZero() {
        this.allIngredientsInStorageHaveAQuantityOfZero();
    }


    @Test
    void whenAddIngredientsForIngredientsInStorage_thenTheirQuantitiesAreUpdated() {
        inventory.addQuantity(AN_EXISTING_INGREDIENT);

        assertEquals(AN_EXISTING_INGREDIENT.getQuantity(), inventory.findIngredientQuantity(AN_EXISTING_INGREDIENT_ID));
    }

    @Test
    void givenIngredientInInventoryWithPositiveQuantity_whenRemoveThisIngredientWithThisQuantity_thenQuantityEqualsZero() {
        inventory.addQuantity(AN_EXISTING_INGREDIENT);
        inventory.removeQuantity(AN_EXISTING_INGREDIENT);
        this.allIngredientsInStorageHaveAQuantityOfZero();
    }

    @Test
    void givenSomeIngredientsQuantitiesPositive_whenDeleteAll_thenStorageIngredientsContainsNoQuantities() {
        inventory.addQuantity(AN_EXISTING_INGREDIENT);
        inventory.reset();
        this.allIngredientsInStorageHaveAQuantityOfZero();
    }

    private void givenIngredientsInInventory() {
        inventory.addIngredient(AN_EXISTING_INGREDIENT);
    }

    private void allIngredientsInStorageHaveAQuantityOfZero() {
        assertEquals(0, inventory.findIngredientQuantity(AN_EXISTING_INGREDIENT_ID));
    }
}