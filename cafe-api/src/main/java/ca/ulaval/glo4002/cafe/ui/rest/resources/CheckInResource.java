package ca.ulaval.glo4002.cafe.ui.rest.resources;

import ca.ulaval.glo4002.cafe.application.checkIn.CheckInService;
import ca.ulaval.glo4002.cafe.domain.customer.Customer;
import ca.ulaval.glo4002.cafe.domain.customer.CustomerId;
import ca.ulaval.glo4002.cafe.ui.rest.DTO.CheckInDTO;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/check-in")
@Consumes(MediaType.APPLICATION_JSON)
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class CheckInResource {
    private final CheckInService checkInService;

    @Inject
    public CheckInResource(CheckInService checkInService) {
        this.checkInService = checkInService;
    }

    @POST
    public Response addCustomer(CheckInDTO checkInDTO) {
        Customer customer = this.buildCustomer(checkInDTO);
        this.checkInService.checkIn(customer);
        return Response
                .status(201)
                .header("Location", "/customers/" + customer.getId().getSerializedValue())
                .build();
    }

    private Customer buildCustomer(CheckInDTO checkInDTO) {
        CustomerId customerId = new CustomerId(checkInDTO.getCustomerId());

        return new Customer(
                customerId,
                checkInDTO.getCustomerName(),
                checkInDTO.getGroupName()
        );
    }
}
