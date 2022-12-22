package ca.ulaval.glo4002.cafe.infrastructure.persistance.repositories;

import ca.ulaval.glo4002.cafe.domain.reservation.NoReservationsFoundException;
import ca.ulaval.glo4002.cafe.domain.reservation.Reservation;
import ca.ulaval.glo4002.cafe.domain.reservation.ReservationRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ReservationRepositoryInMemory implements ReservationRepository {
    private final HashMap<String, Reservation> reservations;

    public ReservationRepositoryInMemory() {
        this.reservations = new HashMap<>();
    }

    public boolean doesReservationExistsForGroup(String groupName) {
        return reservations.containsKey(groupName);
    }

    public void saveReservation(Reservation reservation) {
        this.reservations.put(reservation.getGroupName(), reservation);
    }

    public List<Reservation> getReservations() {
        return new ArrayList<>(this.reservations.values());
    }

    public Reservation findReservationByGroupName(String groupName) {
        return this.reservations.get(groupName);
    }

    public void removeReservationByGroupName(String groupName) {
        if (!this.reservations.containsKey(groupName)) {
            throw new NoReservationsFoundException();
        }
        this.reservations.remove(groupName);
    }

    public int getAmount() {
        return this.reservations.size();
    }

    public void deleteAll() {
        this.reservations.clear();
    }
}
