package ca.ulaval.glo4002.cafe.domain.customer;

import ca.ulaval.glo4002.cafe.domain.bill.Amount;
import ca.ulaval.glo4002.cafe.domain.menu.MenuItem;
import ca.ulaval.glo4002.cafe.domain.menu.MenuItemId;
import ca.ulaval.glo4002.cafe.domain.seat.SeatId;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CustomerTest {

    private static final MenuItem MENU_ITEM_1 = new MenuItem(new MenuItemId("name"), new Amount(1.0f));
    private static final MenuItem MENU_ITEM_2 = new MenuItem(new MenuItemId("name2"), new Amount(1.0f));
    private static final MenuItem MENU_ITEM_3 = new MenuItem(new MenuItemId("name3"), new Amount(1.0f));
    private static List<MenuItem> SOME_MENU_ITEMS_1 = List.of(MENU_ITEM_1, MENU_ITEM_2);
    private static List<MenuItem> SOME_MENU_ITEMS_2 = List.of(MENU_ITEM_3);
    private static List<MenuItem> SOME_MENU_ITEMS_CONCATENATE = List.of(MENU_ITEM_1, MENU_ITEM_2, MENU_ITEM_3);
    @Test
    void whenGetId_thenReturnsId() {
        CustomerId id = new CustomerId("id");
        Customer customer = new Customer(id, "name");

        assertEquals(id, customer.getId());
    }

    @Test
    void whenGetName_thenReturnsName() {
        CustomerId id = new CustomerId("id");
        Customer customer = new Customer(id, "name");

        assertEquals("name", customer.getName());
    }

    @Test
    void whenGetGroupName_thenReturnsGroupName() {
        CustomerId id = new CustomerId("id");
        Customer customer = new Customer(id, "name", "group");

        assertEquals("group", customer.getGroupName());
    }

    @Test
    void whenHasGroup_thenReturnsTrue() {
        CustomerId id = new CustomerId("id");
        Customer customer = new Customer(id, "name", "group");

        assertTrue(customer.hasGroup());
    }

    @Test
    void givenACustomerSeated_whenGetSeatId_thenReturnsSeatId() {
        CustomerId id = new CustomerId("id");
        Customer customer = new Customer(id, "name");
        SeatId seatId = new SeatId(1);
        customer.setSeatId(seatId);

        assertEquals(seatId, customer.getSeatId());
    }

    @Test
    void whenSetSeatId_thenCustomerHaveCorrectSeatId() {
        CustomerId id = new CustomerId("id");
        Customer customer = new Customer(id, "name");
        SeatId seatId = new SeatId(1);
        customer.setSeatId(seatId);

        assertEquals(seatId, customer.getSeatId());
    }

    @Test
    void whenUnsetSeatId_thenSeatIdIsNull() {
        CustomerId id = new CustomerId("id");
        Customer customer = new Customer(id, "name");
        SeatId seatId = new SeatId(1);
        customer.setSeatId(seatId);
        customer.unsetSeatId();

        assertNull(customer.getSeatId());
    }
}