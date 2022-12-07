package ca.ulaval.glo4002.cafe.application.layout;

import ca.ulaval.glo4002.cafe.domain.cube.Cube;
import ca.ulaval.glo4002.cafe.domain.cube.CubesListFactory;
import ca.ulaval.glo4002.cafe.domain.cube.ICubeRepository;
import ca.ulaval.glo4002.cafe.domain.customer.ICustomerRepository;
import ca.ulaval.glo4002.cafe.infrastructure.rest.DTO.LayoutDTO;

import java.util.List;

public class LayoutService {
    private final List<String> cubesNames;
    private final CubesListFactory cubesListFactory;
    private final ICubeRepository cubeRepository;
    private final ICustomerRepository customerRepository;
    private final LayoutDTOAssembler layoutAssembler;
    private String name;
    private int cubeSize;


    public LayoutService(CubesListFactory cubesListFactory, ICubeRepository cubeRepository, ICustomerRepository customerRepository, String name, List<String> cubesNames, int cubeSize, LayoutDTOAssembler layoutAssembler) {
        this.cubesListFactory = cubesListFactory;
        this.cubeRepository = cubeRepository;
        this.customerRepository = customerRepository;
        this.name = name;
        this.cubesNames = cubesNames;
        this.cubeSize = cubeSize;
        this.initializeCubes();
        this.layoutAssembler = layoutAssembler;
    }

    public void initializeCubes() {
        List<Cube> cubes = this.cubesListFactory.create(this.cubesNames, this.cubeSize);
        this.cubeRepository.saveCubes(cubes);
    }

    public LayoutDTO getLayout() {
        return layoutAssembler.createLayoutDTO(this.name, this.cubeRepository.findAll(), this.customerRepository.findAll());
    }

    public void reset() {
        this.cubeRepository.deleteAll();
        this.initializeCubes();
    }

    public void updateConfig(String name, int cubeSize) {
        this.name = name;
        this.cubeSize = cubeSize;
    }

    public String getName() {
        return name;
    }

    public int getCubeSize() {
        return cubeSize;
    }
}
