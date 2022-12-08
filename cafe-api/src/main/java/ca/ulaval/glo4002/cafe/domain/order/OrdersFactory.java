package ca.ulaval.glo4002.cafe.domain.order;

import ca.ulaval.glo4002.cafe.domain.menu.IMenuItemRepository;
import ca.ulaval.glo4002.cafe.domain.menu.MenuItem;
import ca.ulaval.glo4002.cafe.infrastructure.rest.validators.config.InvalidMenuOrderException;

import java.util.List;
import java.util.stream.Collectors;

public class OrdersFactory {
    public Order create(List<MenuItem> menuItems) {
        return new Order(menuItems);
    }

    public List<MenuItem> buildMenuItemListFromStr(List<String> menuItemStrList, IMenuItemRepository menuItemRepository) {
        return menuItemStrList.stream().map(menuItemStr -> searchMenuItem(menuItemRepository, menuItemStr)).collect(Collectors.toList());
    }

    private static MenuItem searchMenuItem(IMenuItemRepository menuItemRepository, String menuItemStr) {
        MenuItem menuItem = menuItemRepository.findMenuItemByName(menuItemStr);
        if (menuItem == null) {
            throw new InvalidMenuOrderException();
        }
        return menuItem;
    }
}
