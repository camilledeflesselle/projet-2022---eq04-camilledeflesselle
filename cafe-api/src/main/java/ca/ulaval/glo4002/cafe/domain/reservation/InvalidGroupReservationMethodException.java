package ca.ulaval.glo4002.cafe.domain.reservation;

public class InvalidGroupReservationMethodException extends RuntimeException {
    public InvalidGroupReservationMethodException() {
        super("The group reservation method is not supported.");
    }
}
