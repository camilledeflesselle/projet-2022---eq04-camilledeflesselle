package ca.ulaval.glo4002.cafe.application.menu.coffees;

import ca.ulaval.glo4002.cafe.application.inventory.IngredientType;
import ca.ulaval.glo4002.cafe.domain.bill.Amount;
import ca.ulaval.glo4002.cafe.domain.inventory.Ingredient;
import ca.ulaval.glo4002.cafe.domain.menu.Coffee;
import ca.ulaval.glo4002.cafe.domain.recipe.Recipe;

import java.util.Map;

import static java.util.Map.entry;

public class Americano extends Coffee {
    public Americano() {
        super("Americano", new Amount(2.25f), new Recipe("Americano", Map.ofEntries(
                entry(new Ingredient("Espresso"), 2),
                entry(new Ingredient("Water"), 3)
        )));
    }
}
