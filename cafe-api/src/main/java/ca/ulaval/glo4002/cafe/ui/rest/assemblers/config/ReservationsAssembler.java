package ca.ulaval.glo4002.cafe.ui.rest.assemblers.config;

import ca.ulaval.glo4002.cafe.domain.reservation.Reservation;
import ca.ulaval.glo4002.cafe.ui.rest.DTO.ReservationDTO;

import java.util.Comparator;
import java.util.List;

public class ReservationsAssembler {
    public List<ReservationDTO> assembleAndSortReservation(List<Reservation> reservations) {
        return reservations
                .stream()
                .sorted(Comparator.comparing(Reservation::getGroupName))
                .map(ReservationDTO::new)
                .toList();
    }
}
