package ca.ulaval.glo4002.cafe.application.close;

import ca.ulaval.glo4002.cafe.domain.bill.IBillRepository;
import ca.ulaval.glo4002.cafe.domain.config.Config;
import ca.ulaval.glo4002.cafe.domain.config.IConfigRepository;
import ca.ulaval.glo4002.cafe.domain.cube.CubesListFactory;
import ca.ulaval.glo4002.cafe.domain.cube.ICubeRepository;
import ca.ulaval.glo4002.cafe.domain.customer.ICustomerRepository;
import ca.ulaval.glo4002.cafe.domain.inventory.IIngredientRepository;
import ca.ulaval.glo4002.cafe.domain.menu.IMenuItemRepository;
import ca.ulaval.glo4002.cafe.domain.order.IOrderRepository;
import ca.ulaval.glo4002.cafe.domain.recipe.IRecipeRepository;
import ca.ulaval.glo4002.cafe.domain.reservation.IReservationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class CloseServiceTest {
    private IReservationRepository reservationRepository;
    private ICustomerRepository customerRepository;
    private IOrderRepository orderRepository;
    private IBillRepository billRepository;
    private ICubeRepository cubeRepository;
    private IMenuItemRepository menuItemRepository;
    private IRecipeRepository recipeRepository;
    private IIngredientRepository ingredientRepository;
    private CubesListFactory cubesListFactory;
    private CloseService closeService;
    private final Config A_CONFIG = new Config();

    @BeforeEach
    public void setUp() {
        reservationRepository = mock(IReservationRepository.class);
        customerRepository = mock(ICustomerRepository.class);
        orderRepository = mock(IOrderRepository.class);
        billRepository = mock(IBillRepository.class);
        cubeRepository = mock(ICubeRepository.class);
        menuItemRepository = mock(IMenuItemRepository.class);
        recipeRepository = mock(IRecipeRepository.class);
        ingredientRepository = mock(IIngredientRepository.class);
        cubesListFactory = mock(CubesListFactory.class);
        IConfigRepository configRepository = mock(IConfigRepository.class);
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

        verify(ingredientRepository).deleteAll();
    }

    @Test
    public void whenClosing_thenANewLayoutIsInitializedAndSaved() {
        closeService.closeCafe();

        verify(cubesListFactory).create(A_CONFIG.getCubesNames(), A_CONFIG.getCubeSize());
        verify(cubeRepository).saveCubes(any());
    }
}
