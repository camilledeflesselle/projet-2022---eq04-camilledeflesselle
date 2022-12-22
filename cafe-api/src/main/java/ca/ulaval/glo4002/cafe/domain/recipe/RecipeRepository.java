package ca.ulaval.glo4002.cafe.domain.recipe;

import ca.ulaval.glo4002.cafe.domain.menu.MenuItemId;

public interface RecipeRepository {
    Recipe findById(MenuItemId recipeName);

    void save(Recipe recipe);

    void deleteAllCustom();
}
