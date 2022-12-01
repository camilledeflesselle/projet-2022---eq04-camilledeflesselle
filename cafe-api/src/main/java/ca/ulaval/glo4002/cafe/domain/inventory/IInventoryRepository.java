package ca.ulaval.glo4002.cafe.domain.inventory;

import java.util.List;
import java.util.Map;

public interface IInventoryRepository {
    List<String> getIngredientsNames();

    Map<Ingredient, Integer> getInventory();

    Integer findIngredientQuantity(Ingredient ingredient);

    void saveIngredients(Map<Ingredient, Integer> inventory);

    void deleteAll();

    void removeIngredients(Map<Ingredient, Integer> ingredientsNeeded);

    void removeQuantity(Ingredient ingredient, Integer quantityNeeded);
}
