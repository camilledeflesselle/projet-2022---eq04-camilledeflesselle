package ca.ulaval.glo4002.cafe.domain.seating;

import ca.ulaval.glo4002.cafe.domain.cube.Cube;
import ca.ulaval.glo4002.cafe.domain.reservation.reservationStrategy.IGroupReservationStrategy;
import ca.ulaval.glo4002.cafe.domain.seat.NoSeatAvailableException;
import ca.ulaval.glo4002.cafe.domain.seat.Seat;
import ca.ulaval.glo4002.cafe.domain.seat.SeatId;
import jakarta.ws.rs.NotFoundException;

import java.util.ArrayList;
import java.util.List;

public class SeatingOrganizer {
    private final List<Cube> cubes;
    private final IGroupReservationStrategy groupReservationStrategy;
    private final int seatsPerCube;

    public SeatingOrganizer(List<Cube> cubes, IGroupReservationStrategy groupReservationStrategy) {
        this.cubes = cubes;
        this.groupReservationStrategy = groupReservationStrategy;
        this.seatsPerCube = cubes.get(0).getSize();
    }

    public List<Cube> getCubes() {
        return this.cubes;
    }

    public Seat getFirstFreeSeat() {
        for (Cube cube : this.cubes) {
            if (cube.hasFreeSeat()) return cube.getFirstFreeSeat();
        }
        throw new NoSeatAvailableException();
    }

    public List<Seat> getFreeSeats() {
        List<Seat> freeSeats = new ArrayList<>();
        for (Cube cube : this.cubes)
            freeSeats.addAll(cube.getFreeSeats());
        return freeSeats;
    }

    public List<SeatId> reserveSeats(int nbSeatToReserve, String groupName) {
        if (nbSeatToReserve > getFreeSeats().size()) throw new NoSeatAvailableException();

        List<Seat> seatsToReserve = this.groupReservationStrategy.getReservationSeats(this, nbSeatToReserve, this.seatsPerCube);

        List<SeatId> reservedSeatsId = new ArrayList<>();
        for (Seat seat : seatsToReserve) {
            seat.reserve(groupName);
            reservedSeatsId.add(seat.getId());
        }
        return reservedSeatsId;
    }

    public Seat findSeatBySeatId(SeatId seatId) {
        for (Cube cube : this.cubes) {
            for (Seat seat : cube.getSeats()) {
                if (seatId.equals(seat.getId())) {
                    return seat;
                }
            }
        }
        throw new NotFoundException();
    }
}
