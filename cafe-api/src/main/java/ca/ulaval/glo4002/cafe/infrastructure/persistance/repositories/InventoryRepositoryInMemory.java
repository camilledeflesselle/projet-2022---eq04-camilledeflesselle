package ca.ulaval.glo4002.cafe.infrastructure.persistance.repositories;

import ca.ulaval.glo4002.cafe.application.inventory.IngredientInLes4Fees;
import ca.ulaval.glo4002.cafe.domain.inventory.Inventory;
import ca.ulaval.glo4002.cafe.domain.inventory.InventoryRepository;

import java.util.Arrays;

public class InventoryRepositoryInMemory implements InventoryRepository {
    private Inventory inventory;

    public InventoryRepositoryInMemory() {
        this.initializeInventory();
    }

    @Override
    public Inventory getInventory() {
        return this.inventory;
    }

    @Override
    public void save(Inventory inventory) {
        this.inventory = inventory;
    }

    @Override
    public void reset() {
        this.inventory.reset();
    }

    private void initializeInventory() {
        this.inventory = new Inventory();
        Arrays.stream(IngredientInLes4Fees.values()).forEach(ingredientId -> inventory.addIngredient(ingredientId.getId(), 0));
    }

}
