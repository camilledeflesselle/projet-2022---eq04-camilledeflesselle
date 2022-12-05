package ca.ulaval.glo4002.cafe.application.menu;

import ca.ulaval.glo4002.cafe.domain.bill.Amount;
import ca.ulaval.glo4002.cafe.domain.menu.Coffee;
import ca.ulaval.glo4002.cafe.domain.menu.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class CoffeeFactory {

    public Coffee createCustomisableCoffee(String name, Amount amount) {
        return new Coffee(name, amount);
    }

    public List<MenuItem> createCoffeesInLes4fees() {
        List<MenuItem> coffees = new ArrayList<>();
        for (CoffeeType type : CoffeeType.values()) {
            coffees.add(this.create(type));
        }
        return coffees;
    }

    private MenuItem create(CoffeeType type) {
        return switch (type) {
            case AMERICANO -> new MenuItem("Americano", new Amount(2.25f));
            case DARK_ROAST -> new MenuItem("Dark Roast", new Amount(2.1f));
            case CAPPUCCINO -> new MenuItem("Cappuccino", new Amount(3.29f));
            case ESPRESSO -> new MenuItem("Espresso", new Amount(2.95f));
            case FLAT_WHITE -> new MenuItem("Flat White", new Amount(3.75f));
            case LATTE -> new MenuItem("Latte", new Amount(2.95f));
            case MACCHIATO -> new MenuItem("Macchiato", new Amount(4.75f));
            case MOCHA -> new MenuItem("Mocha", new Amount(4.15f));
        };
    }
}
