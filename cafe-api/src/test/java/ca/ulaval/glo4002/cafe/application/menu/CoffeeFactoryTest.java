package ca.ulaval.glo4002.cafe.application.menu;

import ca.ulaval.glo4002.cafe.domain.bill.Amount;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CoffeeFactoryTest {

    @Test
    public void whenCreateCoffeesInLes4fees_thenCoffeesAreCreated() {
        CoffeeFactory coffeeFactory = new CoffeeFactory();
        assertFalse(coffeeFactory.createCoffeesInLes4fees().isEmpty());
    }

    @Test
    public void whenCreateAmericanoCoffee_thenCoffeesIsCreatedWithCorrectPrice() {
        CoffeeFactory coffeeFactory = new CoffeeFactory();
        assertEquals(new Amount(2.25f), coffeeFactory.create(CoffeeType.AMERICANO).getPrice());
    }
}