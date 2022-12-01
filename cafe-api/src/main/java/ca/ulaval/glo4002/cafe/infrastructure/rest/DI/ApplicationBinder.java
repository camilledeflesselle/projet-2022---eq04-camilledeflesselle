package ca.ulaval.glo4002.cafe.infrastructure.rest.DI;

import ca.ulaval.glo4002.cafe.application.bill.BillFactory;
import ca.ulaval.glo4002.cafe.application.bill.BillService;
import ca.ulaval.glo4002.cafe.application.checkIn.CheckInService;
import ca.ulaval.glo4002.cafe.application.close.CloseService;
import ca.ulaval.glo4002.cafe.application.cooking.CookingService;
import ca.ulaval.glo4002.cafe.application.customer.CustomerService;
import ca.ulaval.glo4002.cafe.application.inventory.InventoryService;
import ca.ulaval.glo4002.cafe.application.layout.LayoutService;
import ca.ulaval.glo4002.cafe.application.seating.SeatingService;
import ca.ulaval.glo4002.cafe.domain.cube.CubesListFactory;
import ca.ulaval.glo4002.cafe.domain.order.OrdersFactory;
import ca.ulaval.glo4002.cafe.domain.reservation.ReservationFactory;
import ca.ulaval.glo4002.cafe.domain.reservation.reservationStrategy.ReservationStrategyFactory;
import ca.ulaval.glo4002.cafe.domain.seating.SeatingOrganizerFactory;
import ca.ulaval.glo4002.cafe.infrastructure.persistance.repositories.*;
import ca.ulaval.glo4002.cafe.infrastructure.rest.validators.config.ConfigValidator;
import ca.ulaval.glo4002.cafe.infrastructure.rest.validators.inventory.InventoryValidator;
import jakarta.inject.Singleton;
import jakarta.ws.rs.ext.Provider;
import org.glassfish.jersey.internal.inject.AbstractBinder;

import java.util.ArrayList;
import java.util.List;

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

        CubeRepositoryInMemory cubeRepositoryInMemory = new CubeRepositoryInMemory();
        ReservationRepositoryInMemory reservationRepositoryInMemory = new ReservationRepositoryInMemory();
        CustomerRepositoryInMemory customerRepositoryInMemory = new CustomerRepositoryInMemory();
        OrderRepositoryInMemory customerOrdersRepositoryInMemory = new OrderRepositoryInMemory();
        BillRepositoryInMemory billRepositoryInMemory = new BillRepositoryInMemory();
        TaxesRepositoryInMemory taxesRepositoryInMemory = new TaxesRepositoryInMemory();
        MenuItemRepositoryInMemory menuItemRepositoryInMemory = new MenuItemRepositoryInMemory();
        InventoryRepositoryInMemory inventoryRepositoryInMemory = new InventoryRepositoryInMemory();
        RecipeRepositoryInMemory recipeRepositoryInMemory = new RecipeRepositoryInMemory();

        String name = "Les 4-Fées";
        List<String> cubeNames = new ArrayList<>(List.of("Wanda", "Bloom", "Merryweather", "Tinker Bell"));
        int cubeSize = 4;
        LayoutService layoutService = new LayoutService(cubesListFactory, cubeRepositoryInMemory, customerRepositoryInMemory, name, cubeNames, cubeSize);
        BillService billService = new BillService(billFactory, billRepositoryInMemory, taxesRepositoryInMemory, menuItemRepositoryInMemory);
        InventoryService inventoryService = new InventoryService(inventoryRepositoryInMemory);
        CookingService cookingService = new CookingService(recipeRepositoryInMemory, inventoryService);
        CustomerService customerService = new CustomerService(billService, cookingService, customerRepositoryInMemory, customerOrdersRepositoryInMemory, ordersFactory);
        SeatingService seatingService = new SeatingService(reservationStrategyFactory, reservationFactory, seatingOrganizerFactory, cubeRepositoryInMemory, reservationRepositoryInMemory);
        CheckInService checkInService = new CheckInService(customerService, seatingService);
        CloseService closeService = new CloseService(seatingService, billService, customerService, inventoryService, layoutService);

        ConfigValidator configValidator = new ConfigValidator(taxesRepositoryInMemory);
        InventoryValidator inventoryValidator = new InventoryValidator(inventoryRepositoryInMemory);

        bind(customerService).in(Singleton.class);
        bind(seatingService).in(Singleton.class);
        bind(billService).in(Singleton.class);
        bind(checkInService).in(Singleton.class);
        bind(closeService).in(Singleton.class);
        bind(layoutService).in(Singleton.class);
        bind(inventoryService).in(Singleton.class);
        bind(cookingService).in(Singleton.class);

        bind(configValidator).in(Singleton.class);
        bind(inventoryValidator).in(Singleton.class);
    }
}