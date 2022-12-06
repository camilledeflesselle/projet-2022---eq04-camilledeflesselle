package ca.ulaval.glo4002.cafe.application.customer;

import ca.ulaval.glo4002.cafe.application.cooking.CookingService;
import ca.ulaval.glo4002.cafe.domain.customer.Customer;
import ca.ulaval.glo4002.cafe.domain.customer.CustomerDoesNotExistsException;
import ca.ulaval.glo4002.cafe.domain.customer.CustomerId;
import ca.ulaval.glo4002.cafe.domain.customer.ICustomerRepository;
import ca.ulaval.glo4002.cafe.domain.menu.IMenuItemRepository;
import ca.ulaval.glo4002.cafe.domain.menu.MenuItem;
import ca.ulaval.glo4002.cafe.domain.order.IOrderRepository;
import ca.ulaval.glo4002.cafe.domain.order.Order;
import ca.ulaval.glo4002.cafe.domain.order.OrdersFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CustomerServiceTest {
    private static final CustomerId A_CUSTOMER_ID = new CustomerId("id");
    private static final List<String> SOME_MENU_ITEM_NAMES = new ArrayList<>(List.of("item1"));
    private static Customer customerMock;
    private static Order oldOrderMock;
    private static Order newOrderMock;
    private static List<MenuItem> menuItemListMock;
    private static CustomerService customerService;
    private static CookingService cookingServiceMock;
    private static ICustomerRepository customerRepositoryMock;
    private static IOrderRepository orderRepositoryMock;
    private static OrdersFactory ordersFactoryMock;
    private IMenuItemRepository menuItemRepositoryMock;
    private Order concatenatedOrderMock;

    @BeforeEach
    void setUp() {
        cookingServiceMock = mock(CookingService.class);
        customerRepositoryMock = mock(ICustomerRepository.class);
        ordersFactoryMock = mock(OrdersFactory.class);
        customerMock = mock(Customer.class);
        oldOrderMock = mock(Order.class);
        newOrderMock = mock(Order.class);
        concatenatedOrderMock = mock(Order.class);
        menuItemListMock = new ArrayList<>(List.of(mock(MenuItem.class)));
        menuItemRepositoryMock = mock(IMenuItemRepository.class);
        orderRepositoryMock = mock(IOrderRepository.class);
        customerService = new CustomerService(cookingServiceMock, customerRepositoryMock, ordersFactoryMock, menuItemRepositoryMock, orderRepositoryMock);
    }


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
    public void whenInitOrder_thenOrderIsCreated() {
        when(ordersFactoryMock.create(any())).thenReturn(oldOrderMock);

        customerService.initOrder(A_CUSTOMER_ID);

        verify(ordersFactoryMock).create(any());
        verify(orderRepositoryMock).saveOrdersByCustomerId(A_CUSTOMER_ID, oldOrderMock);
    }

    @Test
    public void whenUpdatingCustomerOrders_thenNewOrderIsCreatedFromMenuItems() {
        when(ordersFactoryMock.buildMenuItemListFromStr(SOME_MENU_ITEM_NAMES, menuItemRepositoryMock)).thenReturn(menuItemListMock);
        when(ordersFactoryMock.create(menuItemListMock)).thenReturn(oldOrderMock);
        when(orderRepositoryMock.findOrderByCustomerId(A_CUSTOMER_ID)).thenReturn(oldOrderMock);

        customerService.updateOrdersOfCustomer(A_CUSTOMER_ID, SOME_MENU_ITEM_NAMES);

        verify(ordersFactoryMock).buildMenuItemListFromStr(SOME_MENU_ITEM_NAMES, menuItemRepositoryMock);
        verify(ordersFactoryMock).create(menuItemListMock);
    }

    @Test
    public void whenUpdatingCustomerOrders_thenSearchOrderOfCustomerInStorage() {
        when(ordersFactoryMock.buildMenuItemListFromStr(SOME_MENU_ITEM_NAMES, menuItemRepositoryMock)).thenReturn(menuItemListMock);
        when(ordersFactoryMock.create(menuItemListMock)).thenReturn(oldOrderMock);
        when(orderRepositoryMock.findOrderByCustomerId(A_CUSTOMER_ID)).thenReturn(oldOrderMock);

        customerService.updateOrdersOfCustomer(A_CUSTOMER_ID, SOME_MENU_ITEM_NAMES);

        verify(orderRepositoryMock).findOrderByCustomerId(A_CUSTOMER_ID);
        verify(oldOrderMock).appendMenuItemsFrom(newOrderMock);
    }



    @Test
    public void whenUpdatingCustomerOrders_thenNewOrderIsCooked() {
        when(ordersFactoryMock.buildMenuItemListFromStr(SOME_MENU_ITEM_NAMES, menuItemRepositoryMock)).thenReturn(menuItemListMock);
        when(ordersFactoryMock.create(menuItemListMock)).thenReturn(oldOrderMock);
        when(orderRepositoryMock.findOrderByCustomerId(A_CUSTOMER_ID)).thenReturn(oldOrderMock);
        when(oldOrderMock.appendMenuItemsFrom(newOrderMock)).thenReturn(concatenatedOrderMock);

        customerService.updateOrdersOfCustomer(A_CUSTOMER_ID, SOME_MENU_ITEM_NAMES);

        verify(cookingServiceMock).cookOrder(concatenatedOrderMock);
    }


    @Test
    public void whenUpdatingCustomerOrders_thenUpdatingCustomerOrderIsSaved() {
        when(ordersFactoryMock.buildMenuItemListFromStr(SOME_MENU_ITEM_NAMES, menuItemRepositoryMock)).thenReturn(menuItemListMock);
        when(ordersFactoryMock.create(menuItemListMock)).thenReturn(oldOrderMock);
        when(orderRepositoryMock.findOrderByCustomerId(A_CUSTOMER_ID)).thenReturn(oldOrderMock);
        when(oldOrderMock.appendMenuItemsFrom(newOrderMock)).thenReturn(concatenatedOrderMock);

        customerService.updateOrdersOfCustomer(A_CUSTOMER_ID, SOME_MENU_ITEM_NAMES);

        verify(orderRepositoryMock).saveOrdersByCustomerId(A_CUSTOMER_ID, concatenatedOrderMock);
    }


    @Test
    public void whenReset_thenCustomersAreDeletedFromRepositories() {
        customerService.reset();

        verify(customerRepositoryMock).deleteAll();
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
    }
}