package ca.ulaval.glo4002.cafe.domain.menu;

public interface MenuItemRepository {
    MenuItem findMenuItemByName(String name);

    void save(MenuItem anExistingMenuItem);

    void deleteAllCustom();
}
