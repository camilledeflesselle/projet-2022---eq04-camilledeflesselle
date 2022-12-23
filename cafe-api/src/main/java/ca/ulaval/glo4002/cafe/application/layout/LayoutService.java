package ca.ulaval.glo4002.cafe.application.layout;

import ca.ulaval.glo4002.cafe.domain.config.ConfigRepository;
import ca.ulaval.glo4002.cafe.domain.cube.Cube;
import ca.ulaval.glo4002.cafe.domain.cube.CubeRepository;
import ca.ulaval.glo4002.cafe.domain.cube.CubesListFactory;
import ca.ulaval.glo4002.cafe.domain.customer.CustomerRepository;
import ca.ulaval.glo4002.cafe.ui.rest.DTO.LayoutDTO;

import java.util.List;

public class LayoutService {
    private final CubesListFactory cubesListFactory;
    private final CubeRepository cubeRepository;
    private final CustomerRepository customerRepository;
    private final LayoutDTOAssembler layoutAssembler;
    private final ConfigRepository configRepository;

    public LayoutService(ConfigRepository configRepository, CubesListFactory cubesListFactory, CubeRepository cubeRepository, CustomerRepository customerRepository, LayoutDTOAssembler layoutAssembler) {
        this.configRepository = configRepository;
        this.cubesListFactory = cubesListFactory;
        this.cubeRepository = cubeRepository;
        this.customerRepository = customerRepository;
        this.initializeCubes();
        this.layoutAssembler = layoutAssembler;
    }

    public void initializeCubes() {
        List<Cube> cubes = this.cubesListFactory.create(this.configRepository.findConfig().getCubesNames(), this.configRepository.findConfig().getCubeSize());
        this.cubeRepository.saveCubes(cubes);
    }

    public LayoutDTO getLayout() {
        return layoutAssembler.createLayoutDTO(this.configRepository.findConfig().getName(), this.cubeRepository.findAll(), this.customerRepository.findAll());
    }
}
