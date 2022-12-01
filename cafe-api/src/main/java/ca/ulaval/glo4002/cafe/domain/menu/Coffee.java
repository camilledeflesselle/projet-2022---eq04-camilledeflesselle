package ca.ulaval.glo4002.cafe.domain.menu;

import ca.ulaval.glo4002.cafe.domain.bill.Amount;
import ca.ulaval.glo4002.cafe.domain.recipe.Recipe;

public class Coffee {
    protected String name;
    private final Amount amount;
    private final Recipe recipe;

    public Coffee(String name, Amount amount, Recipe recipe) {
        this.name = name;
        this.amount = amount;
        this.recipe = recipe;
    }
}
