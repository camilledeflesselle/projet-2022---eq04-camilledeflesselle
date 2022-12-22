package ca.ulaval.glo4002.cafe.domain.cube;

import ca.ulaval.glo4002.cafe.domain.seat.Seat;
import ca.ulaval.glo4002.cafe.domain.seat.SeatId;

import java.util.ArrayList;
import java.util.List;

public class Cube {
    private final String name;
    private final List<Seat> seats;

    public Cube(String name, List<Seat> seats) {
        this.name = name;
        this.seats = seats;
    }

    public Cube(String name, int cubeNumber, int cubeSize) {
        this(name, new ArrayList<>());
        this.addSeats(cubeNumber, cubeSize);
    }

    public Cube(String name) {
        this(name, new ArrayList<>());
    }

    public void addSeats(int cubeNumber, int cubeSize) {
        for (int i = 1; i < cubeSize + 1; i++) {
            this.addSeat(cubeNumber * cubeSize + i);
        }
    }

    public void addSeat(int seatNumber) {
        Seat seatToAdd = new Seat(seatNumber);
        this.seats.add(seatToAdd);
    }

    public int getSize() {
        return this.seats.size();
    }

    public Seat getFirstFreeSeat() {
        for (Seat seat : this.seats) {
            if (seat.isAvailable()) {
                return seat;
            }
        }
        return null;
    }

    public String getName() {
        return this.name;
    }

    public List<Seat> getSeats() {
        return this.seats;
    }

    public boolean hasFreeSeat() {
        for (Seat seat : this.seats) {
            if (seat.isAvailable()) {
                return true;
            }
        }
        return false;
    }

    public List<Seat> getFreeSeats() {
        List<Seat> freeSeatsList = new ArrayList<>();
        for (Seat seat : this.seats) {
            if (seat.isAvailable())
                freeSeatsList.add(seat);
        }
        return freeSeatsList;
    }

    public boolean isEmpty() {
        return this.getFreeSeats().size() == this.seats.size();
    }

    public Seat findSeatById(SeatId seatId) {
        for (Seat seat : this.seats) {
            if (seat.getId() == seatId) {
                return seat;
            }
        }
        return null;
    }
}

