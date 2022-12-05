package ca.ulaval.glo4002.cafe.application.inventory;

import ca.ulaval.glo4002.cafe.domain.cooking.InsufficentIngredientsException;
import ca.ulaval.glo4002.cafe.domain.inventory.IInventoryRepository;
import ca.ulaval.glo4002.cafe.domain.inventory.Ingredient;
import ca.ulaval.glo4002.cafe.domain.inventory.IngredientId;
import jakarta.inject.Inject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InventoryService {
    private final IInventoryRepository inventoryRepository;

    @Inject
    public InventoryService(IInventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    public void addIngredientsInInventory(List<Ingredient> ingredients) {
        for (Ingredient ingredient : ingredients) {
            this.inventoryRepository.save(ingredient);
        }
    }

    public Map<IngredientId, Ingredient> getInventory() {
        return this.inventoryRepository.getInventory();
    }

    public void reset() {
        this.inventoryRepository.deleteAll();
    }



    public Map<String, Integer> getInventoryStringify() {
        Map<String, Integer> stringInventory = new HashMap<>();
        Map<IngredientId, Ingredient> inventory = this.getInventory();
        for (IngredientId ingredientId : inventory.keySet()) {
            stringInventory.put(ingredientId.toString(), inventory.get(ingredientId).getQuantity());
        }
        return stringInventory;
    }
}
