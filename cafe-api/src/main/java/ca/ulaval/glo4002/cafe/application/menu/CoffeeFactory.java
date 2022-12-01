package ca.ulaval.glo4002.cafe.application.menu;

import ca.ulaval.glo4002.cafe.application.menu.coffees.*;
import ca.ulaval.glo4002.cafe.domain.bill.Amount;
import ca.ulaval.glo4002.cafe.domain.menu.Coffee;
import ca.ulaval.glo4002.cafe.domain.recipe.Recipe;

public class CoffeeFactory {

    public Coffee createCoffee(CoffeeType type) {
        Coffee coffee = null;

        switch (type) {
            case AMERICANO:
                coffee = new Americano();
                break;
            case DARK_ROAST:
                coffee = new DarkRoast();
                break;
            case CAPPUCCINO:
                coffee = new Cappucino();
                break;
            case ESPRESSO:
                coffee = new Espresso();
                break;
            case FLAT_WHITE:
                coffee = new FlatWhite();
                break;
            case LATTE:
                coffee = new CaffeLatte();
                break;
            case MACCHIATO:
                coffee = new Macchiato();
                break;
            case MOCHA:
                coffee = new Mocha();
                break;
        }
        return coffee;
    }
    
    public Coffee createCustomisableCoffee(String name, Amount amount, Recipe recipe) {
        return new Coffee(name, amount, recipe);
    }
}
