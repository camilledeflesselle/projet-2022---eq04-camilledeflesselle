package ca.ulaval.glo4002.cafe.application.menu.coffees;

import ca.ulaval.glo4002.cafe.domain.menu.Coffee;

import java.util.Map;

import static java.util.Map.entry;

public class Cappucino extends Coffee {
    public Cappucino() {
        super("Cappucino", 2.25f, "Cappucino", Map.ofEntries(
                entry("Espresso", 2),
                entry("Milk", 1)
        ));
    }
}
