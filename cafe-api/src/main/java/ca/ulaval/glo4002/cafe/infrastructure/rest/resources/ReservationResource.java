package ca.ulaval.glo4002.cafe.infrastructure.rest.resources;

import ca.ulaval.glo4002.cafe.application.seating.SeatingService;
import ca.ulaval.glo4002.cafe.domain.reservation.Group;
import ca.ulaval.glo4002.cafe.domain.reservation.Reservation;
import ca.ulaval.glo4002.cafe.infrastructure.rest.DTO.GroupDTO;
import ca.ulaval.glo4002.cafe.infrastructure.rest.DTO.ReservationDTO;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("/reservations")
@Consumes(MediaType.APPLICATION_JSON)
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class ReservationResource {
    private final SeatingService seatingService;

    @Inject
    public ReservationResource(SeatingService seatingService) {
        this.seatingService = seatingService;
    }

    @GET
    public Response getReservations() {
        List<ReservationDTO> reservationDTOS = new ArrayList<>();
        for (Reservation reservation : this.seatingService.getReservations()) {
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
        this.seatingService.addReservation(group);
        return Response
                .ok()
                .build();
    }
}
