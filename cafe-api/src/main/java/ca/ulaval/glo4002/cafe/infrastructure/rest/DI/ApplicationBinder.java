package ca.ulaval.glo4002.cafe.infrastructure.rest.DI;

import ca.ulaval.glo4002.cafe.application.bill.BillFactory;
import ca.ulaval.glo4002.cafe.application.bill.BillService;
import ca.ulaval.glo4002.cafe.application.checkIn.CheckInService;
import ca.ulaval.glo4002.cafe.application.close.CloseService;
import ca.ulaval.glo4002.cafe.application.config.ConfigService;
import ca.ulaval.glo4002.cafe.application.cooking.CookingService;
import ca.ulaval.glo4002.cafe.application.cooking.RecipeFactory;
import ca.ulaval.glo4002.cafe.application.customer.CustomerService;
import ca.ulaval.glo4002.cafe.application.inventory.InventoryService;
import ca.ulaval.glo4002.cafe.application.layout.LayoutDTOAssembler;
import ca.ulaval.glo4002.cafe.application.layout.LayoutService;
import ca.ulaval.glo4002.cafe.application.menu.CoffeeFactory;
import ca.ulaval.glo4002.cafe.application.menu.MenuService;
import ca.ulaval.glo4002.cafe.application.seating.SeatingService;
import ca.ulaval.glo4002.cafe.domain.cube.CubesListFactory;
import ca.ulaval.glo4002.cafe.domain.order.OrdersFactory;
import ca.ulaval.glo4002.cafe.domain.reservation.ReservationFactory;
import ca.ulaval.glo4002.cafe.domain.reservation.reservationStrategy.ReservationStrategyFactory;
import ca.ulaval.glo4002.cafe.domain.seating.SeatingOrganizerFactory;
import ca.ulaval.glo4002.cafe.infrastructure.persistance.repositories.BillRepositoryInMemory;
import ca.ulaval.glo4002.cafe.infrastructure.persistance.repositories.ConfigRepositoryInMemory;
import ca.ulaval.glo4002.cafe.infrastructure.persistance.repositories.CubeRepositoryInMemory;
import ca.ulaval.glo4002.cafe.infrastructure.persistance.repositories.CustomerRepositoryInMemory;
import ca.ulaval.glo4002.cafe.infrastructure.persistance.repositories.IngredientRepositoryInMemory;
import ca.ulaval.glo4002.cafe.infrastructure.persistance.repositories.MenuItemRepositoryInMemory;
import ca.ulaval.glo4002.cafe.infrastructure.persistance.repositories.OrderRepositoryInMemory;
import ca.ulaval.glo4002.cafe.infrastructure.persistance.repositories.RecipeRepositoryInMemory;
import ca.ulaval.glo4002.cafe.infrastructure.persistance.repositories.ReservationRepositoryInMemory;
import ca.ulaval.glo4002.cafe.infrastructure.persistance.repositories.TaxesRepositoryInMemory;
import ca.ulaval.glo4002.cafe.infrastructure.rest.validators.config.ConfigValidator;
import ca.ulaval.glo4002.cafe.infrastructure.rest.validators.inventory.InventoryValidator;
import ca.ulaval.glo4002.cafe.infrastructure.rest.validators.menu.MenuItemAssembler;
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
        ReservationRepositoryInMemory reservationRepositoryInMemory = new ReservationRepositoryInMemory();
        CustomerRepositoryInMemory customerRepositoryInMemory = new CustomerRepositoryInMemory();
        BillRepositoryInMemory billRepositoryInMemory = new BillRepositoryInMemory();
        TaxesRepositoryInMemory taxesRepositoryInMemory = new TaxesRepositoryInMemory();
        MenuItemRepositoryInMemory menuItemRepositoryInMemory = new MenuItemRepositoryInMemory(coffeeFactory);
        IngredientRepositoryInMemory ingredientRepositoryInMemory = new IngredientRepositoryInMemory();
        RecipeRepositoryInMemory recipeRepositoryInMemory = new RecipeRepositoryInMemory(recipeFactory);
        OrderRepositoryInMemory orderRepositoryInMemory = new OrderRepositoryInMemory();
        LayoutDTOAssembler layoutDTOAssembler = new LayoutDTOAssembler();

        LayoutService layoutService = new LayoutService(configRepositoryInMemory, cubesListFactory, cubeRepositoryInMemory, customerRepositoryInMemory, layoutDTOAssembler);
        ConfigService configService = new ConfigService(configRepositoryInMemory);
        BillService billService = new BillService(billFactory, billRepositoryInMemory, configRepositoryInMemory);
        InventoryService inventoryService = new InventoryService(ingredientRepositoryInMemory);
        CookingService cookingService = new CookingService(recipeRepositoryInMemory, ingredientRepositoryInMemory);
        CustomerService customerService = new CustomerService(cookingService, customerRepositoryInMemory, ordersFactory, menuItemRepositoryInMemory, orderRepositoryInMemory);
        SeatingService seatingService = new SeatingService(reservationStrategyFactory, reservationFactory, seatingOrganizerFactory, cubeRepositoryInMemory, reservationRepositoryInMemory);
        CheckInService checkInService = new CheckInService(customerService, seatingService);
        CloseService closeService = new CloseService(configRepositoryInMemory, cubeRepositoryInMemory, reservationRepositoryInMemory, customerRepositoryInMemory, orderRepositoryInMemory,
                billRepositoryInMemory, menuItemRepositoryInMemory, recipeRepositoryInMemory, ingredientRepositoryInMemory, cubesListFactory);
        MenuService menuService = new MenuService(menuItemRepositoryInMemory);

        ConfigValidator configValidator = new ConfigValidator(taxesRepositoryInMemory);
        InventoryValidator inventoryValidator = new InventoryValidator(ingredientRepositoryInMemory);
        MenuItemAssembler menuItemAssembler = new MenuItemAssembler(menuItemRepositoryInMemory);

        bind(configService).in(Singleton.class);
        bind(customerService).in(Singleton.class);
        bind(seatingService).in(Singleton.class);
        bind(billService).in(Singleton.class);
        bind(checkInService).in(Singleton.class);
        bind(closeService).in(Singleton.class);
        bind(layoutService).in(Singleton.class);
        bind(inventoryService).in(Singleton.class);
        bind(cookingService).in(Singleton.class);
        bind(menuService).in(Singleton.class);

        bind(configValidator).in(Singleton.class);
        bind(inventoryValidator).in(Singleton.class);
        bind(menuItemAssembler).in(Singleton.class);
    }
}