package ca.ulaval.glo4002.cafe.domain.order;

import ca.ulaval.glo4002.cafe.domain.menu.IMenuItemRepository;
import ca.ulaval.glo4002.cafe.domain.menu.MenuItem;
import ca.ulaval.glo4002.cafe.domain.menu.MenuItemId;
import ca.ulaval.glo4002.cafe.infrastructure.rest.validators.config.InvalidMenuOrderException;
import jakarta.ws.rs.NotFoundException;

import java.util.ArrayList;
import java.util.List;

public class OrdersFactory {
    public Order create(List<MenuItem> menuItems) {
        return new Order(menuItems);
    }

    public List<MenuItem> buildMenuItemListFromStr(List<String> menuItemStrList, IMenuItemRepository menuItemRepository) {
        List<MenuItem> menuItemList = new ArrayList<>();
        for (String menuItemStr : menuItemStrList) {
            try {
                MenuItem menuItem = menuItemRepository.findMenuItemById(new MenuItemId(menuItemStr));
                menuItemList.add(menuItem);
            } catch (NotFoundException e) {
                throw new InvalidMenuOrderException();
            }
        }
        return menuItemList;
    }
}
