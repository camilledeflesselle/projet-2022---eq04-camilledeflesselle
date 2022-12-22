package ca.ulaval.glo4002.cafe.application.menu;

import ca.ulaval.glo4002.cafe.domain.bill.Amount;
import ca.ulaval.glo4002.cafe.domain.menu.MenuItem;
import ca.ulaval.glo4002.cafe.infrastructure.persistance.repositories.menu.CoffeeFactory;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class CoffeeFactoryTest {

    @Test
    public void whenCreateCoffeesInLes4fees_thenCoffeesAreCreated() {
        CoffeeFactory coffeeFactory = new CoffeeFactory();
        assertFalse(coffeeFactory.createCoffeesInLes4fees().isEmpty());
    }


    @Test
    public void whenCreateAmericanoCoffee_thenCoffeesIsCreatedWithCorrectPrice() {
        CoffeeFactory coffeeFactory = new CoffeeFactory();
        List<MenuItem> coffees = coffeeFactory.createCoffeesInLes4fees();
        assertEquals(new Amount(2.25f), coffees.get(0).getPrice());
    }
}