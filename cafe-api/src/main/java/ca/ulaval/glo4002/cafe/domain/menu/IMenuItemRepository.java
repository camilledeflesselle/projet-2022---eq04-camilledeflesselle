package ca.ulaval.glo4002.cafe.domain.menu;

public interface IMenuItemRepository {
    MenuItem findMenuItemById(MenuItemId id);
    void saveMenuItem(MenuItem anExistingMenuItem);
}
