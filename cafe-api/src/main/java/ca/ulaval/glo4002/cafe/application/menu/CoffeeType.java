package ca.ulaval.glo4002.cafe.application.menu;

import ca.ulaval.glo4002.cafe.domain.menu.MenuItemId;

public enum CoffeeType {
    Americano(new MenuItemId("Americano")),
    Cappuccino(new MenuItemId("Cappuccino")),
    DarkRoast(new MenuItemId("Dark Roast")),
    FlatWhite(new MenuItemId("Flat White")),
    Espresso(new MenuItemId("Espresso")),
    Latte(new MenuItemId("Latte")),
    Macchiato(new MenuItemId("Macchiato")),
    Mocha(new MenuItemId("Mocha"));

    private final MenuItemId id;

    CoffeeType(MenuItemId id) {
        this.id = id;
    }

    public MenuItemId getId() {
        return id;
    }
}
