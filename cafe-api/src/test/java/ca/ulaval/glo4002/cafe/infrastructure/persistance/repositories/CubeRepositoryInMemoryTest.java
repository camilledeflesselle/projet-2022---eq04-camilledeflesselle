package ca.ulaval.glo4002.cafe.infrastructure.persistance.repositories;

import ca.ulaval.glo4002.cafe.domain.cube.Cube;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CubeRepositoryInMemoryTest {
    private static final String A_CUBE_NAME = "Cube1";
    private static final String ANOTHER_CUBE_NAME = "Cube2";

    private CubeRepositoryInMemory cubeRepositoryInMemory;

    @BeforeEach
    public void setup() {
        cubeRepositoryInMemory = new CubeRepositoryInMemory();
    }

    @Test
    public void whenInitialized_thenIsEmpty() {
        assertEquals(0, cubeRepositoryInMemory.getAmount(), "Cubes List is empty when initialized");
    }

    @Test
    public void givenEmptyCubeRepository_whenSavingACube_thenRepositoryHasOneElement() {
        cubeRepositoryInMemory.saveCube(new Cube(A_CUBE_NAME));

        assertEquals(1, cubeRepositoryInMemory.getAmount(), "Cubes List should have only one element");
    }

    @Test
    public void givenEmptyCubeRepository_whenAddingTwoCubes_thenHaveTwoElements() {
        List<Cube> cubes = new ArrayList<>(List.of(new Cube(A_CUBE_NAME), new Cube(ANOTHER_CUBE_NAME)));
        cubeRepositoryInMemory.saveCubes(cubes);

        assertEquals(2, cubeRepositoryInMemory.getAmount(), "Cubes List should have only one element");
    }

    @Test
    public void givenCubeRepositoryMoreThanOneCubes_whenRequestingCubes_thenReturnAllCubes() {
        List<Cube> cubes = new ArrayList<>(List.of(new Cube(A_CUBE_NAME), new Cube(ANOTHER_CUBE_NAME)));
        cubeRepositoryInMemory.saveCubes(cubes);

        List<Cube> returnedCubes = cubeRepositoryInMemory.findAll();

        assertEquals(cubes, returnedCubes);
    }

    @Test
    public void givenNonEmptyCubeRepository_whenDeleteAll_thenRepositoryBecomeEmpty() {
        List<Cube> cubes = new ArrayList<>(List.of(new Cube(A_CUBE_NAME), new Cube(ANOTHER_CUBE_NAME)));
        cubeRepositoryInMemory.saveCubes(cubes);

        cubeRepositoryInMemory.deleteAll();

        assertEquals(0, cubeRepositoryInMemory.getAmount());
    }
}
