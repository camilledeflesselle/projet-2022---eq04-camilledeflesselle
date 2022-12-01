package ca.ulaval.glo4002.cafe.infrastructure.persistance.repositories;

import ca.ulaval.glo4002.cafe.domain.bill.Amount;
import ca.ulaval.glo4002.cafe.domain.order.IMenuItemRepository;
import ca.ulaval.glo4002.cafe.domain.order.MenuItem;
import jakarta.ws.rs.NotFoundException;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class MenuItemRepositoryInMemory implements IMenuItemRepository {
    private final List<MenuItem> menuItems;

    public MenuItemRepositoryInMemory() {
        menuItems = Arrays.asList(
                new MenuItem("Americano", new Amount(2.25f)),
                new MenuItem("Dark Roast", new Amount(2.1f)),
                new MenuItem("Cappuccino", new Amount(3.29f)),
                new MenuItem("Espresso", new Amount(2.95f)),
                new MenuItem("Flat White", new Amount(3.75f)),
                new MenuItem("Latte", new Amount(2.95f)),
                new MenuItem("Macchiato", new Amount(4.75f)),
                new MenuItem("Mocha", new Amount(4.15f))
        );
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
