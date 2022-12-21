package ca.ulaval.glo4002.cafe.infrastructure.persistance.repositories;

import ca.ulaval.glo4002.cafe.application.menu.CoffeeFactory;
import ca.ulaval.glo4002.cafe.domain.menu.IMenuItemRepository;
import ca.ulaval.glo4002.cafe.domain.menu.MenuItem;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MenuItemRepositoryInMemory implements IMenuItemRepository {

    private final List<MenuItem> menuItems = new ArrayList<>();

    public MenuItemRepositoryInMemory(CoffeeFactory coffeeFactory) {
        this.menuItems.addAll(coffeeFactory.createCoffeesInLes4fees());
    }

    @Override
    public MenuItem findMenuItemByName(String name) {
        return menuItems
                .stream()
                .filter(item -> Objects.equals(item.getId().getName(), name))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void saveMenuItem(MenuItem menuItem) {
        menuItems.add(menuItem);
    }

    @Override
    public void deleteAllCustom() {
        menuItems.removeIf(MenuItem::isCustom);
    }
}
