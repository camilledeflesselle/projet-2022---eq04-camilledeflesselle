package ca.ulaval.glo4002.cafe.domain.reservation;

import ca.ulaval.glo4002.cafe.domain.customer.Customer;
import ca.ulaval.glo4002.cafe.domain.seat.NoReservationSeatsException;
import ca.ulaval.glo4002.cafe.domain.seat.SeatId;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class Reservation {
    private final Group group;
    private final List<SeatId> lockedSeatsId;
    private final List<SeatId> reservedSeatsId;

    public Reservation(Group group, List<SeatId> reservedSeatsId) {
        this.group = group;
        this.reservedSeatsId = new ArrayList<>(reservedSeatsId);
        this.lockedSeatsId = new ArrayList<>(reservedSeatsId);
    }

    public int getSize() {
        return this.group.getSize();
    }

    public String getGroupName() {
        return this.group.getName();
    }

    public SeatId popFirstReservedSeatId() {
        if (this.reservedSeatsId.size() < 1) throw new NoReservationSeatsException();
        SeatId reservedSeatId = this.reservedSeatsId.get(0);
        this.reservedSeatsId.remove(0);
        return reservedSeatId;
    }

    public List<SeatId> getLockedSeatsId() {
        return lockedSeatsId;
    }

    public void checkoutCustomer(Customer customer) {
        this.removeSeatIdFromLockedSeatsId(customer.getSeatId());
    }

    private void removeSeatIdFromLockedSeatsId(SeatId seatId) {
        int index = IntStream.range(0, lockedSeatsId.size())
                .filter(i -> lockedSeatsId.get(i) == seatId)
                .findFirst()
                .orElse(-1);

        if (index != -1) lockedSeatsId.remove(index);
    }

    public boolean isEmpty() {
        return lockedSeatsId.size() == reservedSeatsId.size();
    }
}
