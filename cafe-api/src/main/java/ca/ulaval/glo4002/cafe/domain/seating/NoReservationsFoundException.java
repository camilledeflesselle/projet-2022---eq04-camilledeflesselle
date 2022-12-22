package ca.ulaval.glo4002.cafe.domain.seating;

public class NoReservationsFoundException extends RuntimeException {
    public NoReservationsFoundException() {
        super("No reservations were made today for that group.");
    }
}
