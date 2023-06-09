package ca.ulaval.glo4002.cafe.ui.rest.resources;

import ca.ulaval.glo4002.cafe.application.customer.CustomerOrderService;
import ca.ulaval.glo4002.cafe.domain.customer.Customer;
import ca.ulaval.glo4002.cafe.domain.customer.CustomerId;
import ca.ulaval.glo4002.cafe.ui.rest.DTO.OrderDTO;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/customers")
@Consumes(MediaType.APPLICATION_JSON)
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class OrderResource {
    private final CustomerOrderService customerOrderService;

    @Inject
    public OrderResource(CustomerOrderService customerOrderService) {
        this.customerOrderService = customerOrderService;
    }

    @GET
    @Path("/{CUSTOMER_ID}/orders")
    public Response getOrder(@PathParam("CUSTOMER_ID") CustomerId customerId) {
        Customer customer = this.customerOrderService.findCustomer(customerId);
        List<String> orders = this.customerOrderService.findOrder(customer.getId()).getListOfMenuItemNames();
        OrderDTO orderDTO = new OrderDTO(orders);
        return Response
                .ok()
                .entity(orderDTO)
                .build();
    }

    @PUT
    @Path("/{CUSTOMER_ID}/orders")
    public Response putOrder(@PathParam("CUSTOMER_ID") CustomerId customerId, OrderDTO orderDTO) {
        Customer customer = this.customerOrderService.findCustomer(customerId);
        List<String> ordersStr = orderDTO.getOrders();
        this.customerOrderService.updateOrdersOfCustomer(customer.getId(), ordersStr);
        return Response
                .ok()
                .build();
    }
}
