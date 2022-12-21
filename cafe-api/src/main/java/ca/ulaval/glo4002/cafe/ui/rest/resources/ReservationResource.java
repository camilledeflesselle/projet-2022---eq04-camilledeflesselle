package ca.ulaval.glo4002.cafe.ui.rest.resources;

import ca.ulaval.glo4002.cafe.application.reservation.ReservationService;
import ca.ulaval.glo4002.cafe.domain.reservation.Group;
import ca.ulaval.glo4002.cafe.domain.reservation.Reservation;
import ca.ulaval.glo4002.cafe.ui.rest.DTO.GroupDTO;
import ca.ulaval.glo4002.cafe.ui.rest.DTO.ReservationDTO;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.List;

@Path("/reservations")
@Consumes(MediaType.APPLICATION_JSON)
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class ReservationResource {
    private final ReservationService reservationService;

    @Inject
    public ReservationResource(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GET
    public Response getReservations() {
        List<ReservationDTO> reservationDTOS = new ArrayList<>();
        for (Reservation reservation : this.reservationService.getReservations()) {
            reservationDTOS.add(new ReservationDTO(reservation));
        }
        return Response
                .ok()
                .entity(reservationDTOS)
                .build();
    }

    @POST
    public Response getGroupReservations(GroupDTO groupDTO) {
        Group group = new Group(groupDTO.getGroupName(), groupDTO.getGroupSize());
        this.reservationService.addReservation(group);
        return Response
                .ok()
                .build();
    }
}
