package ca.ulaval.glo4002.cafe.domain.seating;

import ca.ulaval.glo4002.cafe.domain.cube.Cube;
import ca.ulaval.glo4002.cafe.domain.customer.Customer;
import ca.ulaval.glo4002.cafe.domain.reservation.IReservationRepository;
import ca.ulaval.glo4002.cafe.domain.reservation.Reservation;
import ca.ulaval.glo4002.cafe.domain.reservation.reservationStrategy.IGroupReservationStrategy;
import ca.ulaval.glo4002.cafe.domain.seat.NoSeatAvailableException;
import ca.ulaval.glo4002.cafe.domain.seat.Seat;
import ca.ulaval.glo4002.cafe.domain.seat.SeatId;
import jakarta.ws.rs.NotFoundException;

import java.util.ArrayList;
import java.util.List;

public class SeatingOrganizer {
    private final List<Cube> cubes;

    public SeatingOrganizer(List<Cube> cubes) {
        this.cubes = cubes;
    }

    public Seat findSeat(Customer customer, IReservationRepository reservationRepository) {
        if (!customer.hasGroup()) {
            return this.getFirstFreeSeat();
        }
        Reservation reservation = reservationRepository.findReservationByGroupName(customer.getGroupName());

        return this.findSeatBySeatId(reservation.popFirstReservedSeatId());
    }

    public List<SeatId> reserveSeats(int nbSeatToReserve, String groupName, IGroupReservationStrategy groupReservationStrategy) {
        if (nbSeatToReserve > this.getFreeSeats().size()) throw new NoSeatAvailableException();

        List<Seat> seatsToReserve = groupReservationStrategy.getReservationSeats(this.cubes, nbSeatToReserve);

        List<SeatId> reservedSeatsId = new ArrayList<>();
        for (Seat seat : seatsToReserve) {
            seat.reserve(groupName);
            reservedSeatsId.add(seat.getId());
        }
        return reservedSeatsId;
    }

    public void removeCustomerFromSeating(Customer customer, IReservationRepository reservationRepository) {
        this.unassignSeatToCustomer(customer, reservationRepository);

        if (customer.hasGroup()) {
            this.removeReservationIfEmpty(customer.getGroupName(), reservationRepository);
        }
    }

    private Seat findSeatBySeatId(SeatId seatId) {
        for (Cube cube : this.cubes) {
            Seat seat = cube.findSeatById(seatId);
            if (seat != null) return seat;
        }
        throw new NotFoundException();
    }

    private Seat getFirstFreeSeat() {
        for (Cube cube : this.cubes) {
            if (cube.hasFreeSeat()) return cube.getFirstFreeSeat();
        }
        throw new NoSeatAvailableException();
    }

    private List<Seat> getFreeSeats() {
        List<Seat> freeSeats = new ArrayList<>();
        for (Cube cube : this.cubes)
            freeSeats.addAll(cube.getFreeSeats());
        return freeSeats;
    }

    private void unassignSeatToCustomer(Customer customer, IReservationRepository reservationRepository) {
        if (customer.hasGroup()) {
            Reservation reservation = reservationRepository.findReservationByGroupName(customer.getGroupName());
            reservation.checkoutCustomer(customer);
        }
        Seat seat = this.findSeatBySeatId(customer.getSeatId());
        seat.unassign();
        customer.unsetSeatId();
    }

    private void removeReservationIfEmpty(String groupName, IReservationRepository reservationRepository) {
        Reservation reservation = reservationRepository.findReservationByGroupName(groupName);
        if (!reservation.isEmpty()) return;

        for (SeatId seatId : reservation.getLockedSeatsId()) {
            this.findSeatBySeatId(seatId).unassign();
        }

        reservationRepository.removeReservationByGroupName(groupName);
    }

}
