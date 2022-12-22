package ca.ulaval.glo4002.cafe.ui.rest.mappers;

import ca.ulaval.glo4002.cafe.domain.customer.CustomerDoesNotExistException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class CustomerDoesNotExistsExceptionMapper implements ExceptionMapper<CustomerDoesNotExistException> {
    @Override
    public Response toResponse(CustomerDoesNotExistException e) {
        return AppExceptionMapperBuilder.buildResponse(e, 404, "INVALID_CUSTOMER_ID");
    }
}
