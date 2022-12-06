package ca.ulaval.glo4002.cafe.domain.customer;

import ca.ulaval.glo4002.cafe.domain.order.Order;
import ca.ulaval.glo4002.cafe.domain.seat.SeatId;

public class Customer {
    private final CustomerId id;
    private final String name;
    private String groupName;
    private SeatId seatId;

    public Customer(CustomerId id, String name, String groupName) {
        this.id = id;
        this.name = name;
        this.groupName = groupName;
    }

    public Customer(CustomerId id, String name) {
        this.id = id;
        this.name = name;
    }

    public CustomerId getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getGroupName() {
        return this.groupName;
    }

    public boolean hasGroup() {
        return this.groupName != null;
    }

    public SeatId getSeatId() {
        return this.seatId;
    }

    public void setSeatId(SeatId seatId) {
        this.seatId = seatId;
    }

    public void unsetSeatId() {
        this.seatId = null;
    }
}
