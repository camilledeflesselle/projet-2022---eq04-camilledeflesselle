package ca.ulaval.glo4002.cafe.infrastructure.persistance.repositories;

import ca.ulaval.glo4002.cafe.application.inventory.IngredientType;
import ca.ulaval.glo4002.cafe.domain.inventory.IInventoryRepository;
import ca.ulaval.glo4002.cafe.domain.inventory.Ingredient;
import ca.ulaval.glo4002.cafe.domain.inventory.IngredientId;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class InventoryRepositoryInMemory implements IInventoryRepository {
    Map<Ingredient, Integer> inventory = new HashMap<>();

    public InventoryRepositoryInMemory() {
        this.addMenuIngredients();
    }

    private void addMenuIngredients() {
        Arrays.stream(IngredientType.values()).map((IngredientType name) -> new Ingredient(name.getLabel(), 0)).forEach(this::save);
    }

    @Override
    public boolean contains(String name) {
        return this.inventory.containsKey(new Ingredient(name));
    }

    @Override
    public Map<IngredientId, Ingredient> getInventory() {
        return this.inventory;
    }

    @Override
    public Ingredient find(Ingredient ingredient) {
        return this.inventory.get(ingredient.getName());
    }

    @Override
    public void save(Ingredient ingredient) {
        this.inventory.put(ingredient.getName(), ingredient.getQuantity());
    }

    @Override
    public void deleteAll() {
        this.inventory.replaceAll((key, value) -> (key, 0));
    }

    @Override
    public Integer findIngredientQuantity(Ingredient ingredient) {
        return this.inventory.get(ingredient);
    }


}
