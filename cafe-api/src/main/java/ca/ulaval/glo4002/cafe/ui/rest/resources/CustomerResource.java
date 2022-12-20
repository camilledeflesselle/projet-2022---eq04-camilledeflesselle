package ca.ulaval.glo4002.cafe.ui.rest.resources;

import ca.ulaval.glo4002.cafe.application.customer.CustomerService;
import ca.ulaval.glo4002.cafe.domain.customer.Customer;
import ca.ulaval.glo4002.cafe.domain.customer.CustomerId;
import ca.ulaval.glo4002.cafe.ui.rest.DTO.CustomerDTO;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/customers")
@Consumes(MediaType.APPLICATION_JSON)
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class CustomerResource {
    private final CustomerService customerService;

    @Inject
    public CustomerResource(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GET
    @Path("/{CUSTOMER_ID}")
    public Response getCustomer(@PathParam("CUSTOMER_ID") CustomerId customerId) {
        Customer customer = this.customerService.findCustomer(customerId);
        CustomerDTO customerDTO = new CustomerDTO(customer.getName(), customer.getSeatId(), customer.getGroupName());
        return Response
                .ok()
                .entity(customerDTO)
                .build();
    }
}
