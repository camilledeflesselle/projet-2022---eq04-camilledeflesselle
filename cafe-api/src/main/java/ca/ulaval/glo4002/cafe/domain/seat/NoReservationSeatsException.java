package ca.ulaval.glo4002.cafe.domain.seat;

public class NoReservationSeatsException extends RuntimeException {
    public NoReservationSeatsException() {
        super("There are no more seats reserved for that group.");
    }
}
