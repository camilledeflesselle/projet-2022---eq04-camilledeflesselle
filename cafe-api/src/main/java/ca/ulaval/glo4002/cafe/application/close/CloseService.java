package ca.ulaval.glo4002.cafe.application.close;

import ca.ulaval.glo4002.cafe.domain.bill.IBillRepository;
import ca.ulaval.glo4002.cafe.domain.config.Config;
import ca.ulaval.glo4002.cafe.domain.config.IConfigRepository;
import ca.ulaval.glo4002.cafe.domain.cube.Cube;
import ca.ulaval.glo4002.cafe.domain.cube.CubesListFactory;
import ca.ulaval.glo4002.cafe.domain.cube.ICubeRepository;
import ca.ulaval.glo4002.cafe.domain.customer.ICustomerRepository;
import ca.ulaval.glo4002.cafe.domain.inventory.IInventoryRepository;
import ca.ulaval.glo4002.cafe.domain.menu.IMenuItemRepository;
import ca.ulaval.glo4002.cafe.domain.order.IOrderRepository;
import ca.ulaval.glo4002.cafe.domain.recipe.IRecipeRepository;
import ca.ulaval.glo4002.cafe.domain.reservation.IReservationRepository;
import java.util.List;

public class CloseService {

    private final IReservationRepository reservationRepository;
    private final ICustomerRepository customerRepository;
    private final IOrderRepository orderRepository;
    private final IBillRepository billRepository;
    private final ICubeRepository cubeRepository;
    private final IMenuItemRepository menuItemRepository;
    private final IRecipeRepository recipeRepository;
    private final IInventoryRepository inventoryRepository;
    private final CubesListFactory cubesListFactory;
    private final IConfigRepository configRepository;

    public CloseService(IConfigRepository configRepository, ICubeRepository cubeRepository, IReservationRepository reservationRepository,
                        ICustomerRepository customerRepository, IOrderRepository orderRepository,
                        IBillRepository billRepository, IMenuItemRepository menuItemRepository,
                        IRecipeRepository recipeRepository, IInventoryRepository inventoryRepository,
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
