package ca.ulaval.glo4002.cafe.application.close;

import ca.ulaval.glo4002.cafe.domain.bill.BillRepository;
import ca.ulaval.glo4002.cafe.domain.config.Config;
import ca.ulaval.glo4002.cafe.domain.config.ConfigRepository;
import ca.ulaval.glo4002.cafe.domain.cube.CubeRepository;
import ca.ulaval.glo4002.cafe.domain.cube.CubesListFactory;
import ca.ulaval.glo4002.cafe.domain.customer.CustomerRepository;
import ca.ulaval.glo4002.cafe.domain.inventory.InventoryRepository;
import ca.ulaval.glo4002.cafe.domain.menu.MenuItemRepository;
import ca.ulaval.glo4002.cafe.domain.order.OrderRepository;
import ca.ulaval.glo4002.cafe.domain.recipe.RecipeRepository;
import ca.ulaval.glo4002.cafe.domain.reservation.ReservationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class CloseServiceTest {

    private static final Config A_CONFIG = new Config();
    private ReservationRepository reservationRepository;
    private CustomerRepository customerRepository;
    private OrderRepository orderRepository;
    private BillRepository billRepository;
    private CubeRepository cubeRepository;
    private MenuItemRepository menuItemRepository;
    private RecipeRepository recipeRepository;
    private InventoryRepository ingredientRepository;
    private CubesListFactory cubesListFactory;
    private CloseService closeService;
    private ConfigRepository configRepository;

    @BeforeEach
    public void setUp() {
        reservationRepository = mock(ReservationRepository.class);
        customerRepository = mock(CustomerRepository.class);
        orderRepository = mock(OrderRepository.class);
        billRepository = mock(BillRepository.class);
        cubeRepository = mock(CubeRepository.class);
        menuItemRepository = mock(MenuItemRepository.class);
        recipeRepository = mock(RecipeRepository.class);
        ingredientRepository = mock(InventoryRepository.class);
        cubesListFactory = mock(CubesListFactory.class);
        configRepository = mock(ConfigRepository.class);
        when(configRepository.findConfig()).thenReturn(A_CONFIG);
        closeService = new CloseService(configRepository, cubeRepository, reservationRepository, customerRepository, orderRepository, billRepository, menuItemRepository, recipeRepository, ingredientRepository, cubesListFactory);
    }

    @Test
    public void whenClosing_thenReservationsAreDeleted() {
        closeService.closeCafe();

        verify(reservationRepository).deleteAll();
    }

    @Test
    public void whenClosing_thenCustomersAreDeleted() {
        closeService.closeCafe();

        verify(customerRepository).deleteAll();
    }

    @Test
    public void whenClosing_thenOrdersAreDeleted() {
        closeService.closeCafe();

        verify(orderRepository).deleteAll();
    }

    @Test
    public void whenClosing_thenBillsAreDeleted() {
        closeService.closeCafe();

        verify(billRepository).deleteAll();
    }

    @Test
    public void whenClosing_thenCubesAreDeleted() {
        closeService.closeCafe();

        verify(cubeRepository).deleteAll();
    }

    @Test
    public void whenClosing_thenCustomMenuItemsAreDeleted() {
        closeService.closeCafe();

        verify(menuItemRepository).deleteAllCustom();
    }

    @Test
    public void whenClosing_thenAllCustomRecipesAreDeleted() {
        closeService.closeCafe();

        verify(recipeRepository).deleteAllCustom();
    }

    @Test
    public void whenClosing_thenAllIngredientsAreDeleted() {
        closeService.closeCafe();

        verify(ingredientRepository).reset();
    }

    @Test
    public void whenClosing_thenANewLayoutIsInitializedAndSaved() {
        closeService.closeCafe();

        verify(cubesListFactory).create(A_CONFIG.getCubesNames(), A_CONFIG.getCubeSize());
        verify(cubeRepository).saveCubes(any());
    }

    @Test
    void whenUpdateConfig_thenConfigIsSavedInStorage() {
        Config config = new Config();
        closeService.updateConfig(config);
        verify(configRepository).saveConfig(config);
    }

    @Test
    void whenUpdateConfig_thenCloseCoffee() {
        Config config = new Config();
        closeService = spy(closeService);
        closeService.updateConfig(config);
        verify(closeService).closeCafe();
    }
}
