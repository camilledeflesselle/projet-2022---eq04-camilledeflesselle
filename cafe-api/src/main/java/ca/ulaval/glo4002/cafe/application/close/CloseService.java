package ca.ulaval.glo4002.cafe.application.close;

import ca.ulaval.glo4002.cafe.domain.bill.BillRepository;
import ca.ulaval.glo4002.cafe.domain.config.Config;
import ca.ulaval.glo4002.cafe.domain.config.ConfigRepository;
import ca.ulaval.glo4002.cafe.domain.cube.Cube;
import ca.ulaval.glo4002.cafe.domain.cube.CubeRepository;
import ca.ulaval.glo4002.cafe.domain.cube.CubesListFactory;
import ca.ulaval.glo4002.cafe.domain.customer.CustomerRepository;
import ca.ulaval.glo4002.cafe.domain.inventory.InventoryRepository;
import ca.ulaval.glo4002.cafe.domain.menu.MenuItemRepository;
import ca.ulaval.glo4002.cafe.domain.order.OrderRepository;
import ca.ulaval.glo4002.cafe.domain.recipe.RecipeRepository;
import ca.ulaval.glo4002.cafe.domain.reservation.ReservationRepository;

import java.util.List;

public class CloseService {

    private final ReservationRepository reservationRepository;
    private final CustomerRepository customerRepository;
    private final OrderRepository orderRepository;
    private final BillRepository billRepository;
    private final CubeRepository cubeRepository;
    private final MenuItemRepository menuItemRepository;
    private final RecipeRepository recipeRepository;
    private final InventoryRepository inventoryRepository;
    private final CubesListFactory cubesListFactory;
    private final ConfigRepository configRepository;

    public CloseService(ConfigRepository configRepository, CubeRepository cubeRepository, ReservationRepository reservationRepository,
                        CustomerRepository customerRepository, OrderRepository orderRepository,
                        BillRepository billRepository, MenuItemRepository menuItemRepository,
                        RecipeRepository recipeRepository, InventoryRepository inventoryRepository,
                        CubesListFactory cubesListFactory) {
        this.configRepository = configRepository;
        this.reservationRepository = reservationRepository;
        this.customerRepository = customerRepository;
        this.orderRepository = orderRepository;
        this.billRepository = billRepository;
        this.cubeRepository = cubeRepository;
        this.cubesListFactory = cubesListFactory;
        this.menuItemRepository = menuItemRepository;
        this.recipeRepository = recipeRepository;
        this.inventoryRepository = inventoryRepository;
    }

    public void updateConfig(Config config) {
        this.configRepository.saveConfig(config);
        this.closeCafe();
    }

    public void closeCafe() {
        billRepository.deleteAll();
        cubeRepository.deleteAll();
        reservationRepository.deleteAll();
        customerRepository.deleteAll();
        orderRepository.deleteAll();
        inventoryRepository.reset();
        menuItemRepository.deleteAllCustom();
        recipeRepository.deleteAllCustom();
        this.initializeLayout();
    }

    private void initializeLayout() {
        List<Cube> cubes = this.cubesListFactory.create(this.configRepository.findConfig().getCubesNames(), this.configRepository.findConfig().getCubeSize());
        this.cubeRepository.saveCubes(cubes);
    }
}
