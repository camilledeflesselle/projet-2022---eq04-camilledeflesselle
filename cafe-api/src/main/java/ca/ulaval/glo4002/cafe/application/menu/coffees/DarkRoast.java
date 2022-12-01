package ca.ulaval.glo4002.cafe.application.menu.coffees;

import ca.ulaval.glo4002.cafe.domain.bill.Amount;
import ca.ulaval.glo4002.cafe.domain.inventory.Ingredient;
import ca.ulaval.glo4002.cafe.domain.menu.Coffee;
import ca.ulaval.glo4002.cafe.domain.recipe.Recipe;

import java.util.Map;

import static java.util.Map.entry;

public class DarkRoast extends Coffee {
    private final String name;
    private final Recipe recipe;
    private final Amount price;
    public DarkRoast() {
        this.name = "Dark Roast";
        this.price = new Amount(2.25f);
        this.recipe = new Recipe("Dark Roast", Map.ofEntries(
               entry(new Ingredient("Espresso"), 2),
               entry(new Ingredient("Water"), 3)
       ));
    }
}
