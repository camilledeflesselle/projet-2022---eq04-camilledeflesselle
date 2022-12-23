package ca.ulaval.glo4002.cafe.domain.inventory;

import ca.ulaval.glo4002.cafe.application.inventory.IngredientInLes4Fees;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InventoryTest {
    private static final IngredientId AN_EXISTING_INGREDIENT_ID = IngredientInLes4Fees.Espresso.getId();
    private static final IngredientId AN_INGREDIENT_ID = new IngredientId("Milk");
    private Ingredients neededIngredients;
    private Inventory inventory;

    @BeforeEach
    public void initializeInventory() {
        inventory =  new Inventory();
        givenIngredientsInInventory();
        neededIngredients = new Ingredients();
    }


    @Test
    void whenAddIngredientsQuantitiesFromIngredients_thenAddThisQuantitiesToInventory() {
        neededIngredients.addIngredient(AN_INGREDIENT_ID, 5);
        inventory.addIngredient(AN_INGREDIENT_ID, 10);

        inventory.addIngredientsQuantitiesFrom(neededIngredients);

        assertEquals(15, inventory.findIngredientQuantity(AN_INGREDIENT_ID));
    }

    @Test
    void whenRemoveIngredientsQuantitiesFromIngredients_thenRemoveThisQuantitiesFromInventory() {
        neededIngredients.addIngredient(AN_INGREDIENT_ID, 5);
        inventory.addIngredient(AN_INGREDIENT_ID, 10);

        inventory.removeIngredients(neededIngredients);

        assertEquals(5, inventory.findIngredientQuantity(AN_INGREDIENT_ID));
    }

    @Test
    public void givenEnoughIngredients_whenHasMoreIngredients_thenReturnTrue() {
        neededIngredients.addIngredient(AN_INGREDIENT_ID, 5);
        inventory.addIngredient(AN_INGREDIENT_ID, 10);
        assertTrue(inventory.hasMoreIngredients(neededIngredients));
    }

    @Test
    public void givenNotEnoughIngredients_whenHasMoreIngredients_thenReturnFalse() {
        neededIngredients.addIngredient(AN_INGREDIENT_ID, 15);
        inventory.addIngredient(AN_INGREDIENT_ID, 10);
        assertFalse(inventory.hasMoreIngredients(neededIngredients));
    }

    @Test
    void whenInitialized_thenAllIngredientsInStorageHaveAQuantityOfZero() {
        this.allIngredientsInStorageHaveAQuantityOfZero();
    }

    @Test
    void givenSomeIngredientsQuantitiesPositive_whenDeleteAll_thenStorageIngredientsContainsNoQuantities() {
        inventory.addIngredient(AN_EXISTING_INGREDIENT_ID, 10);
        inventory.reset();
        this.allIngredientsInStorageHaveAQuantityOfZero();
    }

    private void givenIngredientsInInventory() {
        inventory.addIngredient(AN_EXISTING_INGREDIENT_ID, 0);
    }

    private void allIngredientsInStorageHaveAQuantityOfZero() {
        assertEquals(0, inventory.findIngredientQuantity(AN_EXISTING_INGREDIENT_ID));
    }
}