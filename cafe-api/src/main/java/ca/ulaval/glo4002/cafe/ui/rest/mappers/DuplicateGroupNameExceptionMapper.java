package ca.ulaval.glo4002.cafe.ui.rest.mappers;

import ca.ulaval.glo4002.cafe.domain.reservation.DuplicateGroupNameException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class DuplicateGroupNameExceptionMapper implements ExceptionMapper<DuplicateGroupNameException> {
    @Override
    public Response toResponse(DuplicateGroupNameException e) {
        return AppExceptionMapperBuilder.buildResponse(e, 400, "DUPLICATE_GROUP_NAME");
    }
}
