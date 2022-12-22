package ca.ulaval.glo4002.cafe.domain.reservation.reservationStrategy;

public enum GroupReservationStrategy {
    DEFAULT("Default"),
    FULL_CUBES("Full Cubes"),
    NO_LONERS("No Loners");

    public final String label;

    GroupReservationStrategy(String label) {
        this.label = label;
    }
}
