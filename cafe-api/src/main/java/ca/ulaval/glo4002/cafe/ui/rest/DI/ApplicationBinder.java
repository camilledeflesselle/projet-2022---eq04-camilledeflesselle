package ca.ulaval.glo4002.cafe.ui.rest.DI;

import ca.ulaval.glo4002.cafe.domain.bill.BillFactory;
import ca.ulaval.glo4002.cafe.application.bill.BillService;
import ca.ulaval.glo4002.cafe.application.checkIn.CheckInService;
import ca.ulaval.glo4002.cafe.application.checkOut.CheckOutService;
import ca.ulaval.glo4002.cafe.application.close.CloseService;
import ca.ulaval.glo4002.cafe.application.cooking.CookingService;
import ca.ulaval.glo4002.cafe.application.cooking.RecipeFactory;
import ca.ulaval.glo4002.cafe.application.customer.CustomerService;
import ca.ulaval.glo4002.cafe.application.inventory.InventoryService;
import ca.ulaval.glo4002.cafe.application.layout.LayoutDTOAssembler;
import ca.ulaval.glo4002.cafe.application.layout.LayoutService;
import ca.ulaval.glo4002.cafe.application.menu.MenuService;
import ca.ulaval.glo4002.cafe.application.reservation.ReservationService;
import ca.ulaval.glo4002.cafe.domain.cooking.Cooker;
import ca.ulaval.glo4002.cafe.domain.cube.CubesListFactory;
import ca.ulaval.glo4002.cafe.domain.order.OrdersFactory;
import ca.ulaval.glo4002.cafe.domain.reservation.ReservationFactory;
import ca.ulaval.glo4002.cafe.domain.reservation.reservationStrategy.ReservationStrategyFactory;
import ca.ulaval.glo4002.cafe.domain.seating.SeatingOrganizer;
import ca.ulaval.glo4002.cafe.domain.seating.SeatingOrganizerFactory;
import ca.ulaval.glo4002.cafe.infrastructure.persistance.repositories.*;
import ca.ulaval.glo4002.cafe.infrastructure.persistance.repositories.menu.CoffeeFactory;
import ca.ulaval.glo4002.cafe.infrastructure.persistance.repositories.menu.MenuItemRepositoryInMemory;
import ca.ulaval.glo4002.cafe.infrastructure.persistance.repositories.tax.TaxesRepositoryInMemory;
import ca.ulaval.glo4002.cafe.ui.rest.assemblers.config.ConfigAssembler;
import ca.ulaval.glo4002.cafe.ui.rest.assemblers.inventory.InventoryAssembler;
import ca.ulaval.glo4002.cafe.ui.rest.assemblers.menu.MenuItemAssembler;
import jakarta.inject.Singleton;
import jakarta.ws.rs.ext.Provider;
import org.glassfish.jersey.internal.inject.AbstractBinder;

@Provider
public class ApplicationBinder extends AbstractBinder {

    @Override
    protected void configure() {
        ReservationStrategyFactory reservationStrategyFactory = new ReservationStrategyFactory();
        ReservationFactory reservationFactory = new ReservationFactory();
        BillFactory billFactory = new BillFactory();
        OrdersFactory ordersFactory = new OrdersFactory();
        CubesListFactory cubesListFactory = new CubesListFactory();
        SeatingOrganizerFactory seatingOrganizerFactory = new SeatingOrganizerFactory();
        RecipeFactory recipeFactory = new RecipeFactory();
        CoffeeFactory coffeeFactory = new CoffeeFactory();

        ConfigRepositoryInMemory configRepositoryInMemory = new ConfigRepositoryInMemory();
        CubeRepositoryInMemory cubeRepositoryInMemory = new CubeRepositoryInMemory();
        SeatingOrganizer seatingOrganizer = seatingOrganizerFactory.createSeatingOrganizer(cubeRepositoryInMemory.findAll());
        ReservationRepositoryInMemory reservationRepositoryInMemory = new ReservationRepositoryInMemory();
        CustomerRepositoryInMemory customerRepositoryInMemory = new CustomerRepositoryInMemory();
        BillRepositoryInMemory billRepositoryInMemory = new BillRepositoryInMemory();
        TaxesRepositoryInMemory taxesRepositoryInMemory = new TaxesRepositoryInMemory();
        MenuItemRepositoryInMemory menuItemRepositoryInMemory = new MenuItemRepositoryInMemory(coffeeFactory);
        InventoryRepositoryInMemory ingredientRepositoryInMemory = new InventoryRepositoryInMemory();
        RecipeRepositoryInMemory recipeRepositoryInMemory = new RecipeRepositoryInMemory(recipeFactory);
        OrderRepositoryInMemory orderRepositoryInMemory = new OrderRepositoryInMemory();
        LayoutDTOAssembler layoutDTOAssembler = new LayoutDTOAssembler();
        ca.ulaval.glo4002.cafe.application.inventory.InventoryAssembler inventoryAssembler = new ca.ulaval.glo4002.cafe.application.inventory.InventoryAssembler();

        LayoutService layoutService = new LayoutService(configRepositoryInMemory, cubesListFactory, cubeRepositoryInMemory, customerRepositoryInMemory, layoutDTOAssembler);
        BillService billService = new BillService(billRepositoryInMemory);
        InventoryService inventoryService = new InventoryService(ingredientRepositoryInMemory, inventoryAssembler);
        CookingService cookingService = new CookingService(recipeRepositoryInMemory, ingredientRepositoryInMemory, new Cooker());
        CustomerService customerService = new CustomerService(cookingService, customerRepositoryInMemory, ordersFactory, menuItemRepositoryInMemory, orderRepositoryInMemory);
        ReservationService reservationService = new ReservationService(configRepositoryInMemory, reservationStrategyFactory, reservationFactory, reservationRepositoryInMemory, seatingOrganizer);
        CheckInService checkInService = new CheckInService(customerRepositoryInMemory, seatingOrganizer, ordersFactory, orderRepositoryInMemory, reservationRepositoryInMemory);
        CheckOutService checkOutService = new CheckOutService(customerRepositoryInMemory, orderRepositoryInMemory, configRepositoryInMemory, billFactory, billRepositoryInMemory, seatingOrganizer, reservationRepositoryInMemory);
        CloseService closeService = new CloseService(configRepositoryInMemory, cubeRepositoryInMemory, reservationRepositoryInMemory, customerRepositoryInMemory, orderRepositoryInMemory,
                billRepositoryInMemory, menuItemRepositoryInMemory, recipeRepositoryInMemory, ingredientRepositoryInMemory, cubesListFactory);
        MenuService menuService = new MenuService(menuItemRepositoryInMemory, recipeRepositoryInMemory);

        ConfigAssembler configValidator = new ConfigAssembler(taxesRepositoryInMemory);
        InventoryAssembler inventoryValidator = new InventoryAssembler(ingredientRepositoryInMemory);
        MenuItemAssembler menuItemAssembler = new MenuItemAssembler(menuItemRepositoryInMemory);

        bind(customerService).in(Singleton.class);
        bind(reservationService).in(Singleton.class);
        bind(billService).in(Singleton.class);
        bind(checkInService).in(Singleton.class);
        bind(closeService).in(Singleton.class);
        bind(layoutService).in(Singleton.class);
        bind(inventoryService).in(Singleton.class);
        bind(cookingService).in(Singleton.class);
        bind(menuService).in(Singleton.class);
        bind(checkOutService).in(Singleton.class);

        bind(configValidator).in(Singleton.class);
        bind(inventoryValidator).in(Singleton.class);
        bind(menuItemAssembler).in(Singleton.class);
    }
}