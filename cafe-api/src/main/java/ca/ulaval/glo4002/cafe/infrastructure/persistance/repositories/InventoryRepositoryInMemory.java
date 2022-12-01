package ca.ulaval.glo4002.cafe.infrastructure.persistance.repositories;

import ca.ulaval.glo4002.cafe.domain.inventory.IInventoryRepository;
import ca.ulaval.glo4002.cafe.domain.inventory.Ingredient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InventoryRepositoryInMemory implements IInventoryRepository {
    Map<String, Ingredient> inventory = new HashMap<>();

    public InventoryRepositoryInMemory() {
        this.save(new Ingredient("Chocolate", 0));
        this.save(new Ingredient("Espresso", 0));
        this.save(new Ingredient("Milk", 0));
        this.save(new Ingredient("Water", 0));
    }


    @Override
    public List<String> getIngredientsNames() {
        return this.inventory.keySet().stream().toList();
    }

    @Override
    public Map<String, Ingredient> getInventory() {
        return this.inventory;
    }

    @Override
    public Ingredient findByName(String name) {
        return this.inventory.get(name);
    }

    @Override
    public void save(Ingredient ingredient) {
        this.inventory.put(ingredient.getName(), ingredient);
    }

    @Override
    public void deleteAll() {
        this.inventory.replaceAll((key, value) -> new Ingredient(key, 0));
    }


}
