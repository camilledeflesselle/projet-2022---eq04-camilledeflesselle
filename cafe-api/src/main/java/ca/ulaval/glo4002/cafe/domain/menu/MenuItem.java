package ca.ulaval.glo4002.cafe.domain.menu;

import ca.ulaval.glo4002.cafe.domain.bill.Amount;

public class MenuItem {
    private final String name;
    private final Amount amount;

    public MenuItem(String name, Amount amount) {
        this.name = name;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public Amount getPrice() {
        return amount;
    }
}
