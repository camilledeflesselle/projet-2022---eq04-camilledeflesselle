package ca.ulaval.glo4002.cafe.ui.rest.mappers;

import ca.ulaval.glo4002.cafe.domain.bill.NoBillException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class NoBillExceptionMapper implements ExceptionMapper<NoBillException> {
    @Override
    public Response toResponse(NoBillException e) {
        return AppExceptionMapperBuilder.buildResponse(e, 400, "NO_BILL");
    }
}
