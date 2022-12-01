package ca.ulaval.glo4002.cafe.infrastructure.persistance.repositories;

import ca.ulaval.glo4002.cafe.domain.inventory.IInventoryRepository;
import ca.ulaval.glo4002.cafe.domain.inventory.Ingredient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InventoryRepositoryInMemory implements IInventoryRepository {
    Map<Ingredient, Integer> inventory = new HashMap<>();

    public InventoryRepositoryInMemory() {
        this.inventory.put(new Ingredient("Chocolate"), 0);
        this.inventory.put(new Ingredient("Espresso"), 0);
        this.inventory.put(new Ingredient("Milk"), 0);
        this.inventory.put(new Ingredient("Water"), 0);
    }

    public List<String> getIngredientsNames() {
        List<String> ingredientsNames = new ArrayList<>();
        for (Ingredient ingredient : inventory.keySet()) {
            ingredientsNames.add(ingredient.getName());
        }
        return ingredientsNames;
    }

    @Override
    public Map<Ingredient, Integer> getInventory() {
        return this.inventory;
    }

    @Override
    public Integer findIngredientQuantity(Ingredient ingredient) {
        return this.inventory.get(ingredient);
    }

    @Override
    public void saveIngredients(Map<Ingredient, Integer> inventory) {
        for (Ingredient ingredient : inventory.keySet()) {
            Integer quantity = inventory.get(ingredient);
            this.addQuantity(ingredient, quantity);
        }
    }

    @Override
    public void deleteAll() {
        this.inventory.replaceAll((k, v) -> 0);
    }

    @Override
    public void removeIngredients(Map<Ingredient, Integer> ingredientsNeeded) {
        for (Map.Entry<Ingredient, Integer> ingredient : ingredientsNeeded.entrySet()) {
            Integer quantityNeeded = ingredientsNeeded.get(ingredient.getKey());
            this.removeQuantity(ingredient.getKey(), quantityNeeded);
        }
    }

    private void removeQuantity(Ingredient ingredient, Integer quantityNeeded) {
        this.inventory.put(ingredient, this.inventory.get(ingredient) - quantityNeeded);
    }

    private void addQuantity(Ingredient ingredient, Integer delta) {
        this.inventory.put(ingredient, this.inventory.get(ingredient) + delta);
    }
}
