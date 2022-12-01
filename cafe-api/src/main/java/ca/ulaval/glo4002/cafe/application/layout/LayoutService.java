package ca.ulaval.glo4002.cafe.application.layout;

import ca.ulaval.glo4002.cafe.domain.cube.CubesListFactory;

import ca.ulaval.glo4002.cafe.domain.cube.Cube;
import ca.ulaval.glo4002.cafe.domain.cube.ICubeRepository;
import ca.ulaval.glo4002.cafe.domain.customer.ICustomerRepository;
import ca.ulaval.glo4002.cafe.domain.layout.Layout;

import java.util.List;

public class LayoutService {
    private final List<String> cubesNames;
    private final CubesListFactory cubesListFactory;
    private final ICubeRepository cubeRepository;
    private final ICustomerRepository customerRepository;
    private String name;
    private int cubeSize;
    private Layout layout;


    public LayoutService(CubesListFactory cubesListFactory, ICubeRepository cubeRepository, ICustomerRepository customerRepository, String name, List<String> cubesNames, int cubeSize) {
        this.cubesListFactory = cubesListFactory;
        this.cubeRepository = cubeRepository;
        this.customerRepository = customerRepository;
        this.name = name;
        this.cubesNames = cubesNames;
        this.cubeSize = cubeSize;
        this.initializeLayout();
    }

    public void initializeLayout() {
        List<Cube> cubes = this.cubesListFactory.create(this.cubesNames, this.cubeSize);
        this.cubeRepository.saveCubes(cubes);
        this.layout = new Layout(this.name, this.cubeRepository, this.customerRepository);
    }

    public Layout getLayout() {
        return this.layout;
    }

    public void reset() {
        this.cubeRepository.deleteAll();
        this.initializeLayout();
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
