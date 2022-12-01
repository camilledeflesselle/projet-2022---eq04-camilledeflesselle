package ca.ulaval.glo4002.cafe.domain.cube;

import java.util.List;

public interface ICubeRepository {
    int getAmount();

    void saveCube(Cube cube);

    void saveCubes(List<Cube> cubes);

    List<Cube> findAll();

    void deleteAll();
}
