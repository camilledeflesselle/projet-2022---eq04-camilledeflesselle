package ca.ulaval.glo4002.cafe.ui.rest.mappers;

import ca.ulaval.glo4002.cafe.ui.rest.validators.config.InvalidGroupTipRateException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class InvalidGroupTipRateExceptionMapper implements ExceptionMapper<InvalidGroupTipRateException> {
    @Override
    public Response toResponse(InvalidGroupTipRateException e) {
        return AppExceptionMapperBuilder.buildResponse(e, 400, "INVALID_GROUP_TIP_RATE");
    }
}