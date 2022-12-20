package ca.ulaval.glo4002.cafe.ui.rest.mappers;

import ca.ulaval.glo4002.cafe.domain.seat.NoSeatAvailableException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class NoSeatAvailableExceptionMapper implements ExceptionMapper<NoSeatAvailableException> {
    @Override
    public Response toResponse(NoSeatAvailableException e) {
        return AppExceptionMapperBuilder.buildResponse(e, 400, "INSUFFICIENT_SEATS");
    }
}
