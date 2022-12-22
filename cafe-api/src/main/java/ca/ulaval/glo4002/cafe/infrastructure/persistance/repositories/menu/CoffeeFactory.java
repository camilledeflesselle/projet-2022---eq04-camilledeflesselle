package ca.ulaval.glo4002.cafe.infrastructure.persistance.repositories.menu;

import ca.ulaval.glo4002.cafe.application.menu.CoffeeType;
import ca.ulaval.glo4002.cafe.domain.bill.Amount;
import ca.ulaval.glo4002.cafe.domain.menu.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class CoffeeFactory {

    public List<MenuItem> createCoffeesInLes4fees() {
        List<MenuItem> coffees = new ArrayList<>();
        for (CoffeeType type : CoffeeType.values()) {
            coffees.add(this.create(type));
        }
        return coffees;
    }

    private MenuItem create(CoffeeType type) {
        return switch (type) {
            case Americano -> new MenuItem(type.getId(), new Amount(2.25f));
            case DarkRoast -> new MenuItem(type.getId(), new Amount(2.1f));
            case Cappuccino -> new MenuItem(type.getId(), new Amount(3.29f));
            case Espresso, Latte -> new MenuItem(type.getId(), new Amount(2.95f));
            case FlatWhite -> new MenuItem(type.getId(), new Amount(3.75f));
            case Macchiato -> new MenuItem(type.getId(), new Amount(4.75f));
            case Mocha -> new MenuItem(type.getId(), new Amount(4.15f));
        };
    }
}
