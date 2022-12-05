package ca.ulaval.glo4002.cafe.domain.inventory;

import ca.ulaval.glo4002.cafe.application.inventory.IngredientType;

import java.util.Map;

public interface IInventoryRepository {
    boolean contains(String ingredientName);

    Map<IngredientId, Ingredient> getInventory();

    Ingredient findByName(IngredientType name);

    void save(Ingredient ingredient);

    void deleteAll();

    Integer findIngredientQuantity(String ingredientName);

    Ingredient find(Ingredient ingredientNeeded);
}
