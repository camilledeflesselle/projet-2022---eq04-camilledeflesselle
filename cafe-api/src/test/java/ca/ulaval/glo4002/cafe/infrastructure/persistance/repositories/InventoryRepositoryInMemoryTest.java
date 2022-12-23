package ca.ulaval.glo4002.cafe.infrastructure.persistance.repositories;

import ca.ulaval.glo4002.cafe.application.inventory.IngredientInLes4Fees;
import ca.ulaval.glo4002.cafe.domain.inventory.Ingredient;
import ca.ulaval.glo4002.cafe.domain.inventory.Inventory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InventoryRepositoryInMemoryTest {
    private InventoryRepositoryInMemory inventoryRepository;

    @BeforeEach
    public void initializeRepository() {
        inventoryRepository = new InventoryRepositoryInMemory();
    }

    @Test
    void whenInitialize_thenAllIngredientsOfLes4FeesHaveZeroQuantity() {
        Inventory inventory = inventoryRepository.getInventory();
        allIngredientsHaveNullQuantities(inventory);
    }

    @Test
    void whenSaveInventory_thenInventoryIsUpdated() {
        Inventory inventory = new Inventory();
        inventory.addIngredient(new Ingredient(IngredientInLes4Fees.Milk.getId(), 10));
        inventoryRepository.save(inventory);

        assertEquals(inventory, inventoryRepository.getInventory());
    }

    @Test
    void whenResetInventory_thenAllIngredientsOfLes4FeesHaveZeroQuantity() {
        Inventory inventory = inventoryRepository.getInventory();
        inventory.addIngredient(new Ingredient(IngredientInLes4Fees.Milk.getId(), 10));
        inventoryRepository.save(inventory);

        inventoryRepository.reset();

        allIngredientsHaveNullQuantities(inventory);
    }

    private void allIngredientsHaveNullQuantities(Inventory inventory) {
        assertEquals(0, inventory.findIngredientQuantity(IngredientInLes4Fees.Milk.getId()));
        assertEquals(0, inventory.findIngredientQuantity(IngredientInLes4Fees.Espresso.getId()));
        assertEquals(0, inventory.findIngredientQuantity(IngredientInLes4Fees.Water.getId()));
        assertEquals(0, inventory.findIngredientQuantity(IngredientInLes4Fees.Chocolate.getId()));
    }
}