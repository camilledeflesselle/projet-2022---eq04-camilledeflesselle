package ca.ulaval.glo4002.cafe.ui.rest.mappers;

import ca.ulaval.glo4002.cafe.ui.rest.assemblers.config.InvalidMenuOrderException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class InvalidMenuOrderExceptionMapper implements ExceptionMapper<InvalidMenuOrderException> {
    @Override
    public Response toResponse(InvalidMenuOrderException e) {
        return AppExceptionMapperBuilder.buildResponse(e, 400, "INVALID_MENU_ORDER");
    }
}
