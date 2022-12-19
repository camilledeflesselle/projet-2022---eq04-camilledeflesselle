package ca.ulaval.glo4002.cafe.application.menu;

import ca.ulaval.glo4002.cafe.domain.menu.IMenuItemRepository;
import ca.ulaval.glo4002.cafe.domain.menu.MenuItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class MenuServiceTest {
    private MenuService menuService;
    private IMenuItemRepository menuItemRepository;
    private MenuItem menuItem;

    @BeforeEach
    void setUp() {
        menuItemRepository = mock(IMenuItemRepository.class);
        menuService = new MenuService(menuItemRepository);
        menuItem = mock(MenuItem.class);
    }

    @Test
    void whenAddMenuItem_thenAddMenuItem() {
        menuService.addMenuItem(menuItem);

        verify(menuItemRepository).saveMenuItem(menuItem);
    }
}