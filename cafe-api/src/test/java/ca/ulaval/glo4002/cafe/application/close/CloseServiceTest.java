package ca.ulaval.glo4002.cafe.application.close;

import ca.ulaval.glo4002.cafe.application.bill.BillService;
import ca.ulaval.glo4002.cafe.application.close.CloseService;
import ca.ulaval.glo4002.cafe.application.customer.CustomerService;
import ca.ulaval.glo4002.cafe.application.inventory.InventoryService;
import ca.ulaval.glo4002.cafe.application.layout.LayoutService;
import ca.ulaval.glo4002.cafe.application.seating.SeatingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class CloseServiceTest {
    private static CloseService closeService;
    private static SeatingService seatingServiceMock;
    private static BillService billServiceMock;
    private static CustomerService customerServiceMock;
    private static InventoryService inventoryServiceMock;
    private static LayoutService layoutServiceMock;

    @BeforeEach
    public void setup() {
        seatingServiceMock = mock(SeatingService.class);
        billServiceMock = mock(BillService.class);
        customerServiceMock = mock(CustomerService.class);
        inventoryServiceMock = mock(InventoryService.class);
        layoutServiceMock = mock(LayoutService.class);
        closeService = new CloseService(seatingServiceMock, billServiceMock, customerServiceMock, inventoryServiceMock, layoutServiceMock);
    }

    @Test
    public void whenClosing_eachServiceIsReset() {
        closeService.closeCafe();

        verify(seatingServiceMock).reset();
        verify(billServiceMock).reset();
        verify(customerServiceMock).reset();
        verify(inventoryServiceMock).reset();
        verify(layoutServiceMock).reset();
    }
}
