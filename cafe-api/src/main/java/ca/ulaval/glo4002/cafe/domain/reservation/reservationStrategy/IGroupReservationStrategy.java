package ca.ulaval.glo4002.cafe.domain.reservation.reservationStrategy;

import ca.ulaval.glo4002.cafe.domain.cube.Cube;
import ca.ulaval.glo4002.cafe.domain.seat.Seat;

import java.util.List;

public interface IGroupReservationStrategy {
    List<Seat> getReservationSeats(List<Cube> cubes, int nbToReserve);
}
