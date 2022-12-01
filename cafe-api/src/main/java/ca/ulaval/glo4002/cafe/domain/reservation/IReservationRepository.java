package ca.ulaval.glo4002.cafe.domain.reservation;

import java.util.List;

public interface IReservationRepository {
    boolean doesReservationExistsForGroup(String groupName);

    void saveReservation(Reservation reservation);

    List<Reservation> findReservations();

    Reservation findReservationByGroupName(String groupName);

    void removeReservationByGroupName(String groupName);

    int getAmount();

    void deleteAll();
}
