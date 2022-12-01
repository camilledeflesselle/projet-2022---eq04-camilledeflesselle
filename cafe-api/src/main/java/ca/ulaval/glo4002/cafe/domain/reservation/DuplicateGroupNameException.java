package ca.ulaval.glo4002.cafe.domain.reservation;

public class DuplicateGroupNameException extends RuntimeException {
    public DuplicateGroupNameException() {
        super("The specified group already made a reservation today.");
    }
}
