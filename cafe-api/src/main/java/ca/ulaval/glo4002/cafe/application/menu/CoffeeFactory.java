package ca.ulaval.glo4002.cafe.application.menu;

import ca.ulaval.glo4002.cafe.domain.bill.Amount;
import ca.ulaval.glo4002.cafe.domain.menu.Coffee;

public class CoffeeFactory {

    public Coffee createCustomisableCoffee(String name, Amount amount) {
        return new Coffee(name, amount);
    }
}
