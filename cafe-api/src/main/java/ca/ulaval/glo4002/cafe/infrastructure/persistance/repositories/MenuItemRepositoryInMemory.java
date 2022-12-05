package ca.ulaval.glo4002.cafe.infrastructure.persistance.repositories;

import ca.ulaval.glo4002.cafe.application.menu.CoffeeFactory;
import ca.ulaval.glo4002.cafe.domain.bill.Amount;
import ca.ulaval.glo4002.cafe.domain.menu.IMenuItemRepository;
import ca.ulaval.glo4002.cafe.domain.menu.MenuItem;
import jakarta.ws.rs.NotFoundException;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class MenuItemRepositoryInMemory implements IMenuItemRepository {

    private final List<MenuItem> menuItems;

    public MenuItemRepositoryInMemory(CoffeeFactory coffeeFactory) {
        this.menuItems = coffeeFactory.createCoffeesInLes4fees();
    }

    @Override
    public MenuItem findMenuItemById(String name) {
        MenuItem menuItem = menuItems
                .stream()
                .filter(item -> Objects.equals(item.getName(), name))
                .findFirst()
                .orElse(null);

        if (menuItem == null) {
            throw new NotFoundException();
        }
        return menuItem;
    }
}
