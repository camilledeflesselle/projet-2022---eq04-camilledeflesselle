package ca.ulaval.glo4002.cafe.application.menu;

import ca.ulaval.glo4002.cafe.domain.menu.MenuItem;
import ca.ulaval.glo4002.cafe.domain.menu.MenuItemRepository;
import ca.ulaval.glo4002.cafe.domain.recipe.Recipe;
import ca.ulaval.glo4002.cafe.domain.recipe.RecipeRepository;

public class MenuService {
    private final MenuItemRepository menuItemRepository;
    private final RecipeRepository recipeRepository;

    public MenuService(MenuItemRepository menuItemRepository, RecipeRepository recipeRepository) {
        this.menuItemRepository = menuItemRepository;
        this.recipeRepository = recipeRepository;
    }

    public void addMenuItem(MenuItem menuItem, Recipe recipe) {
        this.menuItemRepository.save(menuItem);
        this.recipeRepository.save(recipe);
    }
}
