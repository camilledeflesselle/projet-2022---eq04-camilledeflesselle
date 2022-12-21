package ca.ulaval.glo4002.cafe.application.menu;

import ca.ulaval.glo4002.cafe.domain.menu.IMenuItemRepository;
import ca.ulaval.glo4002.cafe.domain.menu.MenuItem;
import ca.ulaval.glo4002.cafe.domain.recipe.IRecipeRepository;
import ca.ulaval.glo4002.cafe.domain.recipe.Recipe;

public class MenuService {
    private final IMenuItemRepository menuItemRepository;
    private final IRecipeRepository recipeRepository;

    public MenuService(IMenuItemRepository menuItemRepository, IRecipeRepository recipeRepository) {
        this.menuItemRepository = menuItemRepository;
        this.recipeRepository = recipeRepository;
    }

    public void addMenuItem(MenuItem menuItem, Recipe recipe) {
        this.menuItemRepository.saveMenuItem(menuItem);
        this.recipeRepository.save(recipe);
    }
}
