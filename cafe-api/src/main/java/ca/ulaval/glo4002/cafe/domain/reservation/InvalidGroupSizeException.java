package ca.ulaval.glo4002.cafe.domain.reservation;

public class InvalidGroupSizeException extends RuntimeException {
    public InvalidGroupSizeException() {
        super("Groups must reserve at least two seats.");
    }
}
