package ca.ulaval.glo4002.cafe.application.layout;

import ca.ulaval.glo4002.cafe.domain.config.IConfigRepository;
import ca.ulaval.glo4002.cafe.domain.cube.Cube;
import ca.ulaval.glo4002.cafe.domain.cube.CubesListFactory;
import ca.ulaval.glo4002.cafe.domain.cube.ICubeRepository;
import ca.ulaval.glo4002.cafe.domain.customer.ICustomerRepository;
import ca.ulaval.glo4002.cafe.ui.rest.DTO.LayoutDTO;

import java.util.List;

public class LayoutService {
    private final CubesListFactory cubesListFactory;
    private final ICubeRepository cubeRepository;
    private final ICustomerRepository customerRepository;
    private final LayoutDTOAssembler layoutAssembler;
    private final IConfigRepository configRepository;

    public LayoutService(IConfigRepository configRepository, CubesListFactory cubesListFactory, ICubeRepository cubeRepository, ICustomerRepository customerRepository,  LayoutDTOAssembler layoutAssembler) {
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

    public void reset() {
        this.cubeRepository.deleteAll();
        this.initializeCubes();
    }
}
