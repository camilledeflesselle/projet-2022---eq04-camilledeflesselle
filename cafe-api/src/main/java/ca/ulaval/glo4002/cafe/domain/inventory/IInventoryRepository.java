package ca.ulaval.glo4002.cafe.domain.inventory;

import java.util.List;
import java.util.Map;

public interface IInventoryRepository {
    List<String> getIngredientsNames();

    Map<String, Ingredient> getInventory();

    Ingredient findByName(String name);

    void save(Ingredient ingredient);

    void deleteAll();
}
