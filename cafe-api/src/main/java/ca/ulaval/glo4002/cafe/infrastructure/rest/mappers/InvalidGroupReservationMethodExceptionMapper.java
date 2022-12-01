package ca.ulaval.glo4002.cafe.infrastructure.rest.mappers;

import ca.ulaval.glo4002.cafe.domain.reservation.InvalidGroupReservationMethodException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class InvalidGroupReservationMethodExceptionMapper implements ExceptionMapper<InvalidGroupReservationMethodException> {
    @Override
    public Response toResponse(InvalidGroupReservationMethodException e) {
        return AppExceptionMapperBuilder.buildResponse(e, 400, "INVALID_GROUP_RESERVATION_METHOD");
    }
}
