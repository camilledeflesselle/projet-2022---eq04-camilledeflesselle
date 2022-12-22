package ca.ulaval.glo4002.cafe.domain.menu;

import ca.ulaval.glo4002.cafe.domain.bill.Amount;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


class MenuItemTest {
    private static final MenuItemId AN_ITEM_ID = new MenuItemId("Espresso");
    private static final Amount A_PRICE = new Amount(1.50f);
    private static final MenuItem AN_ITEM = new MenuItem(AN_ITEM_ID, A_PRICE);

    @Test
    void whenGetId_thenReturnsIdOfItem() {
        assertEquals(AN_ITEM_ID, AN_ITEM.getId());
    }

    @Test
    void whenGetPrice_thenReturnsPriceOfItem() {
        assertEquals(A_PRICE, AN_ITEM.getPrice());
    }

}