package ca.ulaval.glo4002.cafe.infrastructure.persistance.repositories;

import ca.ulaval.glo4002.cafe.application.inventory.IngredientType;
import ca.ulaval.glo4002.cafe.domain.inventory.IInventoryRepository;
import ca.ulaval.glo4002.cafe.domain.inventory.Ingredient;
import ca.ulaval.glo4002.cafe.domain.inventory.IngredientId;
import ca.ulaval.glo4002.cafe.domain.inventory.Inventory;
import java.util.Arrays;

public class InventoryRepositoryInMemory implements IInventoryRepository {
    Inventory inventory;

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
        Arrays.stream(IngredientType.values()).map((IngredientType name) -> new Ingredient(new IngredientId(name.getLabel()), 0)).forEach(inventory::addIngredient);
    }

}
