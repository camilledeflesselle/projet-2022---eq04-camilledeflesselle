package ca.ulaval.glo4002.cafe.domain.reservation.reservationStrategy;

import ca.ulaval.glo4002.cafe.domain.seat.Seat;
import ca.ulaval.glo4002.cafe.domain.seating.SeatingOrganizer;

import java.util.List;

public interface IGroupReservationStrategy {
    List<Seat> getReservationSeats(SeatingOrganizer seatingOrganizer, int nbToReserve, int seatsPerCube);
}
