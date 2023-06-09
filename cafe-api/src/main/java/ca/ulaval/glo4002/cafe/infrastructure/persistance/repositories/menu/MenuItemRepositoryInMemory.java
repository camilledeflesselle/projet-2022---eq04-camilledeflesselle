package ca.ulaval.glo4002.cafe.infrastructure.persistance.repositories.menu;

import ca.ulaval.glo4002.cafe.domain.menu.MenuItem;
import ca.ulaval.glo4002.cafe.domain.menu.MenuItemRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MenuItemRepositoryInMemory implements MenuItemRepository {

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
    public void save(MenuItem menuItem) {
        menuItems.add(menuItem);
    }

    @Override
    public void deleteAllCustom() {
        menuItems.removeIf(MenuItem::isCustom);
    }
}
