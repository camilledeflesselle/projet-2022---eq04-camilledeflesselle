package ca.ulaval.glo4002.cafe.ui.rest.mappers;

import ca.ulaval.glo4002.cafe.domain.reservation.NoReservationsFoundException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class NoReservationsFoundExceptionMapper implements ExceptionMapper<NoReservationsFoundException> {
    @Override
    public Response toResponse(NoReservationsFoundException e) {
        return AppExceptionMapperBuilder.buildResponse(e, 400, "NO_RESERVATIONS_FOUND");
    }
}
