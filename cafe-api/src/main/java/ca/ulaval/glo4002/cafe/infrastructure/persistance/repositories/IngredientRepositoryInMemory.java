package ca.ulaval.glo4002.cafe.infrastructure.persistance.repositories;

import ca.ulaval.glo4002.cafe.application.inventory.IngredientType;
import ca.ulaval.glo4002.cafe.domain.inventory.IIngredientRepository;
import ca.ulaval.glo4002.cafe.domain.inventory.Ingredient;
import ca.ulaval.glo4002.cafe.domain.inventory.IngredientId;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class IngredientRepositoryInMemory implements IIngredientRepository {
    Map<IngredientId, Ingredient> inventory = new HashMap<>();

    public IngredientRepositoryInMemory() {
        this.addMenuIngredients();
    }

    private void addMenuIngredients() {
        Arrays.stream(IngredientType.values()).map((IngredientType name) -> new Ingredient(new IngredientId(name.getLabel()), 0)).forEach(this::save);
    }

    @Override
    public boolean contains(String name) {
        return this.inventory.containsKey(new IngredientId(name));
    }

    @Override
    public Map<IngredientId, Ingredient> getInventory() {
        return this.inventory;
    }

    @Override
    public Ingredient findByName(IngredientId id) {
        return this.inventory.get(id);
    }

    @Override
    public void removeIngredient(Ingredient ingredient) {
        this.inventory.get(ingredient.getId()).use(ingredient.getQuantity());
    }

    @Override
    public void save(Ingredient ingredient) {
        this.inventory.put(ingredient.getId(), ingredient);
    }

    @Override
    public void deleteAll() {
        this.inventory.forEach((key, value) -> value.useAll());
    }

    public Integer findIngredientQuantity(IngredientId id) {
        return this.inventory.get(id).getQuantity();
    }


}
