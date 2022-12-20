package ca.ulaval.glo4002.cafe.ui.rest.mappers;

import ca.ulaval.glo4002.cafe.domain.seat.NoReservationSeatsException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class NoReservationSeatsExceptionMapper implements ExceptionMapper<NoReservationSeatsException> {
    @Override
    public Response toResponse(NoReservationSeatsException e) {
        return AppExceptionMapperBuilder.buildResponse(e, 400, "NO_GROUP_SEATS");
    }
}
