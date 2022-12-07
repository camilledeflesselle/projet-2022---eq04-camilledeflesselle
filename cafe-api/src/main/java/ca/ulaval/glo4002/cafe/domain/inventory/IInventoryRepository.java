package ca.ulaval.glo4002.cafe.domain.inventory;

import java.util.Map;

public interface IInventoryRepository {
    boolean contains(String ingredientName);

    Map<IngredientId, Ingredient> getInventory();

    Ingredient findByName(IngredientId name);

    void save(Ingredient ingredient);

    void deleteAll();

    Integer findIngredientQuantity(IngredientId id);

    void removeIngredient(Ingredient ingredient);
}
