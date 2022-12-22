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
            case AMERICANO -> new MenuItem(type.getId(), new Amount(2.25f));
            case DARK_ROAST -> new MenuItem(type.getId(), new Amount(2.1f));
            case CAPPUCCINO -> new MenuItem(type.getId(), new Amount(3.29f));
            case ESPRESSO, LATTE -> new MenuItem(type.getId(), new Amount(2.95f));
            case FLAT_WHITE -> new MenuItem(type.getId(), new Amount(3.75f));
            case MACCHIATO -> new MenuItem(type.getId(), new Amount(4.75f));
            case MOCHA -> new MenuItem(type.getId(), new Amount(4.15f));
        };
    }
}
