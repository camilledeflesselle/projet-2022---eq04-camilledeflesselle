package ca.ulaval.glo4002.cafe.application.customer;

import ca.ulaval.glo4002.cafe.application.bill.BillService;
import ca.ulaval.glo4002.cafe.application.cooking.CookingService;
import ca.ulaval.glo4002.cafe.domain.customer.Customer;
import ca.ulaval.glo4002.cafe.domain.customer.CustomerDoesNotExistsException;
import ca.ulaval.glo4002.cafe.domain.customer.CustomerId;
import ca.ulaval.glo4002.cafe.domain.customer.ICustomerRepository;
import ca.ulaval.glo4002.cafe.domain.order.IOrderRepository;
import ca.ulaval.glo4002.cafe.domain.menu.MenuItem;
import ca.ulaval.glo4002.cafe.domain.order.Order;
import ca.ulaval.glo4002.cafe.domain.order.OrdersFactory;
import jakarta.ws.rs.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class CustomerServiceTest {
    private static final CustomerId A_CUSTOMER_ID = new CustomerId("id");
    private static final List<String> SOME_MENU_ITEM_NAMES = new ArrayList<>(List.of("item1"));

    private static Customer customerMock;
    private static Order existingOrderMock;
    private static Order newOrderMock;
    private static List<MenuItem> menuItemListMock;
    private static CustomerService customerService;
    private static BillService billServiceMock;
    private static CookingService cookingServiceMock;
    private static ICustomerRepository customerRepositoryMock;
    private static OrdersFactory ordersFactoryMock;

    @BeforeEach
    void setUp() {
        billServiceMock = mock(BillService.class);
        cookingServiceMock = mock(CookingService.class);
        customerRepositoryMock = mock(ICustomerRepository.class);
        ordersFactoryMock = mock(OrdersFactory.class);
        customerMock = mock(Customer.class);
        existingOrderMock = mock(Order.class);
        newOrderMock = mock(Order.class);
        menuItemListMock = new ArrayList<>(List.of(mock(MenuItem.class)));
        customerService = new CustomerService(billServiceMock, cookingServiceMock, customerRepositoryMock, ordersFactoryMock);
    }

    /*
    @Test
    public void whenSearchingCustomer_thenSearchRepositoryWithCustomerId() {
        customerService.findCustomer(A_CUSTOMER_ID);

        verify(customerRepositoryMock).findCustomerByCustomerId(A_CUSTOMER_ID);
    }

    @Test
    public void whenSavingCustomer_thenCustomerIsSavedInRepository() {
        customerService.saveCustomer(customerMock);

        verify(customerRepositoryMock).saveCustomer(customerMock);
    }

    @Test
    public void givenNoOrderForCustomer_whenSearchingOrder_thenCreateNewOrder() {
        Order order = new Order(new ArrayList<>());
        when(ordersFactoryMock.create(any())).thenReturn(order);

        customerService.findOrCreateEmptyOrder(A_CUSTOMER_ID);

        verify(ordersFactoryMock).create(any(ArrayList.class));
    }

    @Test
    public void whenSearchingForCustomerOrders_thenSearchInTheRepositoryWithCustomerId() {
        customerService.findOrCreateEmptyOrder(A_CUSTOMER_ID);

        verify(orderRepositoryMock, times(2)).findOrderByCustomerId(A_CUSTOMER_ID);
    }


    @Test
    public void whenUpdatingCustomerOrders_thenMenuItemsArePreparedFromBillService() {
        when(billServiceMock.buildMenuItemListFromStr(any())).thenReturn(menuItemListMock);
        when(orderRepositoryMock.findOrderByCustomerId(any())).thenReturn(existingOrderMock);
        customerService.updateOrdersOfCustomer(A_CUSTOMER_ID, SOME_MENU_ITEM_NAMES);

        verify(billServiceMock).buildMenuItemListFromStr(SOME_MENU_ITEM_NAMES);
    }

    @Test
    public void whenUpdatingCustomerOrders_thenNewOrderIsCreatedFromMenuItems() {
        when(billServiceMock.buildMenuItemListFromStr(any())).thenReturn(menuItemListMock);
        when(orderRepositoryMock.findOrderByCustomerId(any())).thenReturn(existingOrderMock);
        customerService.updateOrdersOfCustomer(A_CUSTOMER_ID, SOME_MENU_ITEM_NAMES);

        verify(ordersFactoryMock).create(menuItemListMock);
    }

    @Test
    public void whenUpdatingCustomerOrders_thenNewOrderIsCooked() {
        when(billServiceMock.buildMenuItemListFromStr(any())).thenReturn(menuItemListMock);
        when(ordersFactoryMock.create(any())).thenReturn(newOrderMock);
        when(orderRepositoryMock.findOrderByCustomerId(any())).thenReturn(existingOrderMock);
        customerService.updateOrdersOfCustomer(A_CUSTOMER_ID, SOME_MENU_ITEM_NAMES);

        verify(cookingServiceMock).cookOrder(newOrderMock);
    }

    @Test
    public void whenUpdatingCustomerOrders_thenExistingOrderSearchedFromRepository() {
        when(billServiceMock.buildMenuItemListFromStr(any())).thenReturn(menuItemListMock);
        when(orderRepositoryMock.findOrderByCustomerId(any())).thenReturn(existingOrderMock);
        customerService.updateOrdersOfCustomer(A_CUSTOMER_ID, SOME_MENU_ITEM_NAMES);

        verify(orderRepositoryMock, times(2)).findOrderByCustomerId(A_CUSTOMER_ID);
    }

    @Test
    public void whenUpdatingCustomerOrders_thenExistingOrderIsUpdatedAndSaved() {
        when(billServiceMock.buildMenuItemListFromStr(any())).thenReturn(menuItemListMock);
        when(ordersFactoryMock.create(any())).thenReturn(newOrderMock);
        when(orderRepositoryMock.findOrderByCustomerId(any())).thenReturn(existingOrderMock);
        customerService.updateOrdersOfCustomer(A_CUSTOMER_ID, SOME_MENU_ITEM_NAMES);

        verify(existingOrderMock).appendMenuItemsFrom(newOrderMock);
        verify(orderRepositoryMock).saveOrdersByCustomerId(A_CUSTOMER_ID, existingOrderMock);
    }

    @Test
    public void givenNoOrderForCustomer_whenUpdatingCustomerOrders_thenNewOrderIsSaved() {
        when(billServiceMock.buildMenuItemListFromStr(any())).thenReturn(menuItemListMock);
        when(ordersFactoryMock.create(any())).thenReturn(newOrderMock);
        when(orderRepositoryMock.findOrderByCustomerId(any())).thenThrow(NotFoundException.class);

        customerService.updateOrdersOfCustomer(A_CUSTOMER_ID, SOME_MENU_ITEM_NAMES);

        verify(orderRepositoryMock).saveOrdersByCustomerId(A_CUSTOMER_ID, newOrderMock);
    }

    @Test
    public void whenReset_thenCustomersAndOrdersAreDeletedFromRepositories() {
        customerService.reset();

        verify(customerRepositoryMock).deleteAll();
        verify(orderRepositoryMock).deleteAll();
    }

    @Test
    public void whenCustomHasAlreadyVisited_thenReturnTrue() {
        when(customerRepositoryMock.findCustomerByCustomerId(A_CUSTOMER_ID)).thenReturn(customerMock);

        boolean hasAlreadyVisited = customerService.hasAlreadyVisited(customerMock);

        assertTrue(hasAlreadyVisited);
    }

    @Test
    public void whenCustomHasNotVisited_thenReturnFalse() {
        when(customerRepositoryMock.findCustomerByCustomerId(A_CUSTOMER_ID)).thenThrow(CustomerDoesNotExistsException.class);
        when(customerMock.getId()).thenReturn(A_CUSTOMER_ID);
        boolean hasAlreadyVisited = customerService.hasAlreadyVisited(customerMock);

        assertFalse(hasAlreadyVisited);
    }*/
}