package ca.ulaval.glo4002.cafe.domain.seat;

public class NoSeatAvailableException extends RuntimeException {
    public NoSeatAvailableException() {
        super("There are currently no available seats. Please come back later.");
    }
}
