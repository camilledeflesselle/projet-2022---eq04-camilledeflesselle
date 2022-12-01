package ca.ulaval.glo4002.cafe.domain.menu;

import ca.ulaval.glo4002.cafe.domain.bill.Amount;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


class MenuItemTest {
    private static final String AN_ITEM_NAME = "Espresso";
    private static final Amount A_PRICE = new Amount(1.50f);
    private static final MenuItem AN_ITEM = new MenuItem(AN_ITEM_NAME, A_PRICE);

    @Test
    void whenGetName_thenReturnsNameOfItem() {
        assertEquals(AN_ITEM_NAME, AN_ITEM.getName());
    }

    @Test
    void whenGetPrice_thenReturnsPriceOfItem() {
        assertEquals(A_PRICE, AN_ITEM.getPrice());
    }
}