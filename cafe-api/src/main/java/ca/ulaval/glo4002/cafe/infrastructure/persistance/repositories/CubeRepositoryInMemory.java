package ca.ulaval.glo4002.cafe.infrastructure.persistance.repositories;

import ca.ulaval.glo4002.cafe.domain.cube.Cube;
import ca.ulaval.glo4002.cafe.domain.cube.ICubeRepository;
import java.util.ArrayList;
import java.util.List;

public class CubeRepositoryInMemory implements ICubeRepository {
    private final List<Cube> cubes;

    public CubeRepositoryInMemory() {
        this.cubes = new ArrayList<>();
    }

    public int getAmount() {
        return cubes.size();
    }

    public void saveCube(Cube cube) {
        this.cubes.add(cube);
    }

    public void saveCubes(List<Cube> newCubes) {
        this.cubes.addAll(newCubes);
    }

    public List<Cube> findAll() {
        return cubes;
    }

    public void deleteAll() {
        this.cubes.clear();
    }
}
