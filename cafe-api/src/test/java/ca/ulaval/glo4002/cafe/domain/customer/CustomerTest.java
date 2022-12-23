package ca.ulaval.glo4002.cafe.domain.customer;

import ca.ulaval.glo4002.cafe.domain.seat.SeatId;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomerTest {

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