package ca.ulaval.glo4002.cafe.infrastructure.rest.mappers;

import ca.ulaval.glo4002.cafe.domain.inventory.InsufficentIngredientsException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class InsufficentIngredientsExceptionMapper implements ExceptionMapper<InsufficentIngredientsException> {
    @Override
    public Response toResponse(InsufficentIngredientsException e) {
        return AppExceptionMapperBuilder.buildResponse(e, 400, "INSUFFICIENT_INGREDIENTS");
    }
}

