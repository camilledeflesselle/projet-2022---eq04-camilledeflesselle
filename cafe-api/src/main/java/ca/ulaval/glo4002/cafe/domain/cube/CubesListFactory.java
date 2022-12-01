package ca.ulaval.glo4002.cafe.domain.cube;

import java.util.ArrayList;
import java.util.List;

public class CubesListFactory {
    List<Cube> cubes;
    
    private void addCube(Cube cube) {
        this.cubes.add(cube);
    }

    public List<Cube> create(List<String> cubesNames, int cubeSize) {
        this.cubes = new ArrayList<>();
        cubesNames.sort(String::compareToIgnoreCase);
        int index = 0;
        for (String cubeName : cubesNames) {
            this.addCube(new Cube(cubeName, index++, cubeSize));
        }
        return this.cubes;
    }
}
