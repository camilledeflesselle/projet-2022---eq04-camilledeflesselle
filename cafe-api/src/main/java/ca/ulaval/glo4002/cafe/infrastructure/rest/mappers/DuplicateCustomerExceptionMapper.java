package ca.ulaval.glo4002.cafe.infrastructure.rest.mappers;

import ca.ulaval.glo4002.cafe.domain.customer.DuplicateCustomerException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class DuplicateCustomerExceptionMapper implements ExceptionMapper<DuplicateCustomerException> {
    @Override
    public Response toResponse(DuplicateCustomerException e) {
        return AppExceptionMapperBuilder.buildResponse(e, 400, "DUPLICATE_CUSTOMER_ID");
    }
}