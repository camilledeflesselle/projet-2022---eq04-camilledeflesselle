package ca.ulaval.glo4002.cafe.infrastructure.rest.mappers;

import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class NotFoundExceptionMapper implements ExceptionMapper<NotFoundException> {
    @Override
    public Response toResponse(NotFoundException e) {
        return AppExceptionMapperBuilder.buildResponse(e, 400, "NOT_FOUND");
    }
}