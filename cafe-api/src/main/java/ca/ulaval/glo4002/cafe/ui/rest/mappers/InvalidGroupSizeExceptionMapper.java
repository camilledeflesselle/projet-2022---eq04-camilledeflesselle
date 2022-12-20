package ca.ulaval.glo4002.cafe.ui.rest.mappers;

import ca.ulaval.glo4002.cafe.domain.reservation.InvalidGroupSizeException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class InvalidGroupSizeExceptionMapper implements ExceptionMapper<InvalidGroupSizeException> {
    @Override
    public Response toResponse(InvalidGroupSizeException e) {
        return AppExceptionMapperBuilder.buildResponse(e, 400, "INVALID_GROUP_SIZE");
    }
}
