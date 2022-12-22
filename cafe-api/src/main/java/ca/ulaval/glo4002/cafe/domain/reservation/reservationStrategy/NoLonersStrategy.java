package ca.ulaval.glo4002.cafe.domain.reservation.reservationStrategy;

import ca.ulaval.glo4002.cafe.domain.cube.Cube;
import ca.ulaval.glo4002.cafe.domain.seat.NoSeatAvailableException;
import ca.ulaval.glo4002.cafe.domain.seat.Seat;

import java.util.ArrayList;
import java.util.List;

/**
 * No loners strategy, the goal is to avoid loners.
 */
public class NoLonersStrategy implements ReservationStrategy {
    private int nbRemainToReserve;
    private List<Seat> seatToReserve;
    private List<Seat> freeSeatsInCube;

    @Override
    public List<Seat> getReservationSeats(List<Cube> cubes, int nbToReserve) {
        this.seatToReserve = new ArrayList<>();
        this.nbRemainToReserve = nbToReserve;
        for (Cube cube : cubes) {
            this.freeSeatsInCube = cube.getFreeSeats();

            if (this.freeSeatsInCube.size() <= 1) {
                continue;

            } else if (this.nbRemainToReserve <= this.freeSeatsInCube.size()) {
                reserveAllRemainingRequestsInCube();
                break;

            } else if (((this.nbRemainToReserve - 1) == this.freeSeatsInCube.size()) && (this.nbRemainToReserve > 3)) {
                reserveRemainingRequestsLeavingTwo();

            } else if (nbRemainToReserve > 3) {
                reserveEntireCube();
            }
        }
        if (this.nbRemainToReserve > 0) {
            throw new NoSeatAvailableException();
        }
        return this.seatToReserve;
    }

    private void reserveAllRemainingRequestsInCube() {
        for (int i = 0; i < this.nbRemainToReserve; i++) {
            Seat seat = this.freeSeatsInCube.get(i);
            this.seatToReserve.add(seat);
        }
        this.nbRemainToReserve = 0;
    }

    private void reserveRemainingRequestsLeavingTwo() {
        int nbSeatStillToReserve = this.nbRemainToReserve - 2;
        for (int i = 0; i < nbSeatStillToReserve; i++) {
            Seat seat = this.freeSeatsInCube.get(i);
            this.seatToReserve.add(seat);
            this.nbRemainToReserve -= 1;
        }
    }

    private void reserveEntireCube() {
        this.seatToReserve.addAll(this.freeSeatsInCube);
        this.nbRemainToReserve -= this.freeSeatsInCube.size();
    }
}
