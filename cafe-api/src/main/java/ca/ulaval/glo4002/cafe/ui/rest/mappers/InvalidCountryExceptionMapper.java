package ca.ulaval.glo4002.cafe.ui.rest.mappers;

import ca.ulaval.glo4002.cafe.ui.rest.assemblers.config.InvalidCountryException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class InvalidCountryExceptionMapper implements ExceptionMapper<InvalidCountryException> {
    @Override
    public Response toResponse(InvalidCountryException e) {
        return AppExceptionMapperBuilder.buildResponse(e, 400, "INVALID_COUNTRY");
    }
}
