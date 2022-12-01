package ca.ulaval.glo4002.cafe.infrastructure.rest.mappers;

import ca.ulaval.glo4002.cafe.domain.customer.CustomerDoesNotExistsException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class CustomerDoesNotExistsExceptionMapper implements ExceptionMapper<CustomerDoesNotExistsException> {
    @Override
    public Response toResponse(CustomerDoesNotExistsException e) {
        return AppExceptionMapperBuilder.buildResponse(e, 404, "INVALID_CUSTOMER_ID");
    }
}
