package ca.ulaval.glo4002.cafe.domain.reservation.reservationStrategy;

public enum GroupReservationStrategy {
    Default("Default"),
    FullCubes("Full Cubes"),
    NoLoners("No Loners");

    public final String label;

    GroupReservationStrategy(String label) {
        this.label = label;
    }
}
