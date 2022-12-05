package ca.ulaval.glo4002.cafe.application.menu;

import ca.ulaval.glo4002.cafe.domain.menu.MenuItemId;

public enum CoffeeType {
    AMERICANO(new MenuItemId("Americano")),
    CAPPUCCINO(new MenuItemId("Cappuccino")),
    DARK_ROAST(new MenuItemId("Dark Roast")),
    FLAT_WHITE(new MenuItemId("Flat White")),
    ESPRESSO(new MenuItemId("Espresso")),
    LATTE(new MenuItemId("Latte")),
    MACCHIATO(new MenuItemId("Macchiato")),
    MOCHA(new MenuItemId("Mocha"));

    private final MenuItemId id;

    CoffeeType(MenuItemId id) {
        this.id = id;
    }

    public MenuItemId getId() {
        return id;
    }
}
