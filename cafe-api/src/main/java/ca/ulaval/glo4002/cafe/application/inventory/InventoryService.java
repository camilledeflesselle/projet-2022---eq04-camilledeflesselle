package ca.ulaval.glo4002.cafe.application.inventory;

import ca.ulaval.glo4002.cafe.domain.cooking.InsufficentIngredientsException;
import ca.ulaval.glo4002.cafe.domain.inventory.IInventoryRepository;
import ca.ulaval.glo4002.cafe.domain.inventory.Ingredient;
import jakarta.inject.Inject;

import java.util.HashMap;
import java.util.Map;

public class InventoryService {
    private final IInventoryRepository inventoryRepository;

    @Inject
    public InventoryService(IInventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    public void addIngredientsInInventory(Map<Ingredient, Integer> inventory) {
        this.inventoryRepository.saveIngredients(inventory);
    }

    public Map<Ingredient, Integer> getInventory() {
        return this.inventoryRepository.getInventory();
    }

    public void reset() {
        this.inventoryRepository.deleteAll();
    }

    public boolean isEnoughIngredients(Map<Ingredient, Integer> ingredientsNeeded) {
        for (Ingredient ingredient : ingredientsNeeded.keySet()) {
            Integer quantityNeeded = ingredientsNeeded.get(ingredient);
            Integer quantityInInventory = this.inventoryRepository.findIngredientQuantity(ingredient);
            if (quantityNeeded > quantityInInventory) {
                return false;
            }
        }
        return true;
    }

    public void removeIngredients(Map<Ingredient, Integer> ingredientsNeeded) {
        if (this.isEnoughIngredients(ingredientsNeeded)) {
            this.inventoryRepository.removeIngredients(ingredientsNeeded);
        } else {
            throw new InsufficentIngredientsException();
        }
    }

    public Map<String, Integer> getInventoryStringify() {
        Map<String, Integer> stringInventory = new HashMap<>();
        Map<Ingredient, Integer> inventory = this.getInventory();
        for (Ingredient ingredient : inventory.keySet()) {
            stringInventory.put(ingredient.getName(), inventory.get(ingredient));
        }
        return stringInventory;
    }
}
