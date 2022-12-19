package ca.ulaval.glo4002.cafe.application.menu;

import ca.ulaval.glo4002.cafe.domain.menu.IMenuItemRepository;
import ca.ulaval.glo4002.cafe.domain.menu.MenuItem;

public class MenuService {
    private final IMenuItemRepository menuItemRepository;

    public MenuService(IMenuItemRepository menuItemRepository) {
        this.menuItemRepository = menuItemRepository;
    }

    public void addMenuItem(MenuItem menuItem) {
        this.menuItemRepository.saveMenuItem(menuItem);
    }
}
