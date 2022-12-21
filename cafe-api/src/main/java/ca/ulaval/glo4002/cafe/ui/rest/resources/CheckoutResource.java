package ca.ulaval.glo4002.cafe.ui.rest.resources;

import ca.ulaval.glo4002.cafe.application.bill.BillService;
import ca.ulaval.glo4002.cafe.application.checkOut.CheckOutService;
import ca.ulaval.glo4002.cafe.application.customer.CustomerService;
import ca.ulaval.glo4002.cafe.application.seating.SeatingService;
import ca.ulaval.glo4002.cafe.domain.customer.CustomerId;
import ca.ulaval.glo4002.cafe.ui.rest.DTO.CheckoutDTO;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/checkout")
@Consumes(MediaType.APPLICATION_JSON)
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class CheckoutResource {
    private final CheckOutService checkOutService;

    @Inject
    public CheckoutResource(CustomerService customerService, BillService billService, SeatingService seatingService) {
        this.checkOutService = new CheckOutService(customerService, seatingService, billService);
    }

    @POST
    public Response checkoutCustomer(CheckoutDTO checkoutDTO) {
        CustomerId customerId = new CustomerId(checkoutDTO.getCustomerId());
        this.checkOutService.checkoutCustomer(customerId);
        return Response
                .status(201)
                .header("Location", "/customers/" + customerId.getSerializedValue() + "/bill")
                .build();
    }
}