package ca.ulaval.glo4002.cafe.application.close;

import ca.ulaval.glo4002.cafe.application.bill.BillService;
import ca.ulaval.glo4002.cafe.application.customer.CustomerService;
import ca.ulaval.glo4002.cafe.application.inventory.InventoryService;
import ca.ulaval.glo4002.cafe.application.layout.LayoutService;
import ca.ulaval.glo4002.cafe.application.seating.SeatingService;

public class CloseService {
    SeatingService seatingService;
    BillService billService;
    CustomerService customerService;
    LayoutService layoutService;
    InventoryService inventoryService;

    public CloseService(SeatingService seatingService, BillService billService, CustomerService customerService, InventoryService inventoryService, LayoutService layoutService) {
        this.seatingService = seatingService;
        this.billService = billService;
        this.customerService = customerService;
        this.inventoryService = inventoryService;
        this.layoutService = layoutService;
    }

    public void closeCafe() {
        this.seatingService.reset();
        this.billService.reset();
        this.customerService.reset();
        this.inventoryService.reset();
        this.layoutService.reset();
    }
}
