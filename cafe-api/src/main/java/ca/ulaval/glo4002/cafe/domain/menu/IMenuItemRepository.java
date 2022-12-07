package ca.ulaval.glo4002.cafe.domain.menu;

public interface IMenuItemRepository {
    MenuItem findMenuItemByName(String name);
    void saveMenuItem(MenuItem anExistingMenuItem);
}
