package ca.ulaval.glo4002.cafe.ui.rest.resources;

import ca.ulaval.glo4002.cafe.application.bill.BillService;
import ca.ulaval.glo4002.cafe.application.customer.CustomerOrderService;
import ca.ulaval.glo4002.cafe.domain.bill.Bill;
import ca.ulaval.glo4002.cafe.domain.customer.Customer;
import ca.ulaval.glo4002.cafe.domain.customer.CustomerId;
import ca.ulaval.glo4002.cafe.ui.rest.DTO.BillDTO;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/customers")
@Consumes(MediaType.APPLICATION_JSON)
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class BillResource {
    private final BillService billService;
    private final CustomerOrderService customerOrderService;

    @Inject
    public BillResource(BillService billService, CustomerOrderService customerOrderService) {
        this.billService = billService;
        this.customerOrderService = customerOrderService;
    }

    @GET
    @Path("/{CUSTOMER_ID}/bill")
    public Response getCustomerBill(@PathParam("CUSTOMER_ID") CustomerId customerId) {
        Customer customer = this.customerOrderService.findCustomer(customerId);
        Bill bill = this.billService.getBillByCustomerId(customer.getId());
        BillDTO billDTO = new BillDTO(bill);
        return Response
                .ok()
                .entity(billDTO)
                .build();
    }
}
