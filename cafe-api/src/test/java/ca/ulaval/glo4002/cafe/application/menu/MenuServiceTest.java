package ca.ulaval.glo4002.cafe.application.menu;

import ca.ulaval.glo4002.cafe.domain.menu.IMenuItemRepository;
import ca.ulaval.glo4002.cafe.domain.menu.MenuItem;
import ca.ulaval.glo4002.cafe.domain.recipe.IRecipeRepository;
import ca.ulaval.glo4002.cafe.domain.recipe.Recipe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class MenuServiceTest {
    private MenuService menuService;
    private IMenuItemRepository menuItemRepository;
    private MenuItem menuItem;
    private IRecipeRepository recipeRepository;
    private Recipe recipe;

    @BeforeEach
    void setUp() {
        menuItemRepository = mock(IMenuItemRepository.class);
        recipeRepository = mock(IRecipeRepository.class);
        menuService = new MenuService(menuItemRepository, recipeRepository);
        menuItem = mock(MenuItem.class);
        recipe = mock(Recipe.class);
    }

    @Test
    void whenAddMenuItem_thenAddMenuItem() {
        menuService.addMenuItem(menuItem, recipe);

        verify(menuItemRepository).saveMenuItem(menuItem);
        verify(recipeRepository).save(recipe);
    }
}