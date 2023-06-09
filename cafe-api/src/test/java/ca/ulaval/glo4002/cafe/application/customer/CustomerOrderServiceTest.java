package ca.ulaval.glo4002.cafe.application.customer;

import ca.ulaval.glo4002.cafe.application.cooking.CookingService;
import ca.ulaval.glo4002.cafe.domain.customer.CustomerDoesNotExistException;
import ca.ulaval.glo4002.cafe.domain.customer.CustomerId;
import ca.ulaval.glo4002.cafe.domain.customer.CustomerRepository;
import ca.ulaval.glo4002.cafe.domain.menu.MenuItem;
import ca.ulaval.glo4002.cafe.domain.menu.MenuItemRepository;
import ca.ulaval.glo4002.cafe.domain.order.Order;
import ca.ulaval.glo4002.cafe.domain.order.OrderRepository;
import ca.ulaval.glo4002.cafe.domain.order.OrdersFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class CustomerOrderServiceTest {
    private static final CustomerId A_CUSTOMER_ID = new CustomerId("id");
    private static final List<String> SOME_MENU_ITEM_NAMES = new ArrayList<>(List.of("item1"));
    private static Order oldOrderMock;
    private static Order newOrderMock;
    private static List<MenuItem> menuItemListMock;
    private static CustomerOrderService customerOrderService;
    private static CookingService cookingServiceMock;
    private static CustomerRepository customerRepositoryMock;
    private static OrderRepository orderRepositoryMock;
    private static OrdersFactory ordersFactoryMock;
    private MenuItemRepository menuItemRepositoryMock;
    private Order concatenatedOrderMock;

    @BeforeEach
    void setUp() {
        cookingServiceMock = mock(CookingService.class);
        customerRepositoryMock = mock(CustomerRepository.class);
        ordersFactoryMock = mock(OrdersFactory.class);
        oldOrderMock = mock(Order.class);
        newOrderMock = mock(Order.class);
        concatenatedOrderMock = mock(Order.class);
        menuItemListMock = new ArrayList<>(List.of(mock(MenuItem.class)));
        menuItemRepositoryMock = mock(MenuItemRepository.class);
        orderRepositoryMock = mock(OrderRepository.class);
        customerOrderService = new CustomerOrderService(cookingServiceMock, customerRepositoryMock, ordersFactoryMock, menuItemRepositoryMock, orderRepositoryMock);
    }


    @Test
    public void whenSearchingNotExistingCustomer_thenRaiseCustomerDoesNotExistsException() {
        assertThrows(CustomerDoesNotExistException.class, () -> customerOrderService.findCustomer(A_CUSTOMER_ID));

        verify(customerRepositoryMock).findCustomerByCustomerId(A_CUSTOMER_ID);
    }


    @Test
    public void whenUpdatingCustomerOrders_thenNewOrderIsCreatedFromMenuItems() {
        when(ordersFactoryMock.buildMenuItemListFromStr(SOME_MENU_ITEM_NAMES, menuItemRepositoryMock)).thenReturn(menuItemListMock);
        when(ordersFactoryMock.create(menuItemListMock)).thenReturn(oldOrderMock);
        when(orderRepositoryMock.findOrderByCustomerId(A_CUSTOMER_ID)).thenReturn(oldOrderMock);

        customerOrderService.updateOrdersOfCustomer(A_CUSTOMER_ID, SOME_MENU_ITEM_NAMES);

        verify(ordersFactoryMock).buildMenuItemListFromStr(SOME_MENU_ITEM_NAMES, menuItemRepositoryMock);
        verify(ordersFactoryMock).create(menuItemListMock);
    }

    @Test
    public void whenUpdatingCustomerOrders_thenSearchOrderOfCustomerInStorage() {
        when(ordersFactoryMock.buildMenuItemListFromStr(SOME_MENU_ITEM_NAMES, menuItemRepositoryMock)).thenReturn(menuItemListMock);
        when(ordersFactoryMock.create(menuItemListMock)).thenReturn(newOrderMock);
        when(orderRepositoryMock.findOrderByCustomerId(A_CUSTOMER_ID)).thenReturn(oldOrderMock);

        customerOrderService.updateOrdersOfCustomer(A_CUSTOMER_ID, SOME_MENU_ITEM_NAMES);

        verify(orderRepositoryMock).findOrderByCustomerId(A_CUSTOMER_ID);
        verify(oldOrderMock).appendMenuItemsFrom(newOrderMock);
    }



    @Test
    public void whenUpdatingCustomerOrders_thenNewOrderIsCooked() {
        when(ordersFactoryMock.buildMenuItemListFromStr(SOME_MENU_ITEM_NAMES, menuItemRepositoryMock)).thenReturn(menuItemListMock);
        when(ordersFactoryMock.create(menuItemListMock)).thenReturn(newOrderMock);
        when(orderRepositoryMock.findOrderByCustomerId(A_CUSTOMER_ID)).thenReturn(oldOrderMock);
        when(oldOrderMock.appendMenuItemsFrom(newOrderMock)).thenReturn(concatenatedOrderMock);

        customerOrderService.updateOrdersOfCustomer(A_CUSTOMER_ID, SOME_MENU_ITEM_NAMES);

        verify(cookingServiceMock).cookOrder(newOrderMock);
    }


    @Test
    public void whenUpdatingCustomerOrders_thenUpdatingCustomerOrderIsSaved() {
        when(ordersFactoryMock.buildMenuItemListFromStr(SOME_MENU_ITEM_NAMES, menuItemRepositoryMock)).thenReturn(menuItemListMock);
        when(ordersFactoryMock.create(menuItemListMock)).thenReturn(newOrderMock);
        when(orderRepositoryMock.findOrderByCustomerId(A_CUSTOMER_ID)).thenReturn(oldOrderMock);
        when(oldOrderMock.appendMenuItemsFrom(newOrderMock)).thenReturn(concatenatedOrderMock);

        customerOrderService.updateOrdersOfCustomer(A_CUSTOMER_ID, SOME_MENU_ITEM_NAMES);

        verify(orderRepositoryMock).saveOrdersByCustomerId(A_CUSTOMER_ID, concatenatedOrderMock);
    }

}