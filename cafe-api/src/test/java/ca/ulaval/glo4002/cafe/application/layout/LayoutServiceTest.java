package ca.ulaval.glo4002.cafe.application.layout;

import ca.ulaval.glo4002.cafe.domain.cube.Cube;
import ca.ulaval.glo4002.cafe.domain.cube.CubesListFactory;
import ca.ulaval.glo4002.cafe.domain.cube.ICubeRepository;
import ca.ulaval.glo4002.cafe.domain.customer.ICustomerRepository;
import ca.ulaval.glo4002.cafe.domain.layout.Layout;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class LayoutServiceTest {
    private static final String A_CAFE_NAME = "Les 4-fées";
    private static final List<String> SOME_CUBES_NAME = new ArrayList<>(List.of("Wanda", "Bloom", "Merryweather", "Tinker Bell"));
    private static final int A_CUBE_SIZE = 4;

    private static CubesListFactory cubesListFactoryMock;
    private static ICubeRepository cubeRepositoryMock;
    private static ICustomerRepository customerRepositoryMock;
    private static List<Cube> cubesListMock;

    @BeforeEach
    public void setup() {
        cubesListFactoryMock = mock(CubesListFactory.class);
        cubeRepositoryMock = mock(ICubeRepository.class);
        customerRepositoryMock = mock(ICustomerRepository.class);
        cubesListMock = new ArrayList<>(List.of(mock(Cube.class)));
    }

    @Test
    public void whenInitialized_cubesAreCreatedFromCubeNamesAndCubeSize() {
        new LayoutService(cubesListFactoryMock, cubeRepositoryMock, customerRepositoryMock, A_CAFE_NAME, SOME_CUBES_NAME, A_CUBE_SIZE);

        verify(cubesListFactoryMock).create(SOME_CUBES_NAME, A_CUBE_SIZE);
    }

    @Test
    public void whenInitialized_cubesAreSavedInRepository() {
        when(cubesListFactoryMock.create(SOME_CUBES_NAME, A_CUBE_SIZE)).thenReturn(cubesListMock);
        new LayoutService(cubesListFactoryMock, cubeRepositoryMock, customerRepositoryMock, A_CAFE_NAME, SOME_CUBES_NAME, A_CUBE_SIZE);

        verify(cubeRepositoryMock).saveCubes(cubesListMock);
    }

    @Test
    public void whenInitialized_layoutIsCreatedFromNameAndRepositories() {
        LayoutService layoutService = new LayoutService(cubesListFactoryMock, cubeRepositoryMock, customerRepositoryMock, A_CAFE_NAME, SOME_CUBES_NAME, A_CUBE_SIZE);

        Layout expectedLayout = new Layout(A_CAFE_NAME, cubeRepositoryMock, customerRepositoryMock);

        assertEquals(expectedLayout, layoutService.getLayout());
    }

    @Test
    public void whenReset_cubesAreDeletedFromRepository() {
        LayoutService layoutService = new LayoutService(cubesListFactoryMock, cubeRepositoryMock, customerRepositoryMock, A_CAFE_NAME, SOME_CUBES_NAME, A_CUBE_SIZE);

        layoutService.reset();

        verify(cubeRepositoryMock).deleteAll();
    }

    @Test
    public void whenReset_cubesAreCreatedFromCubeNamesAndCubeSize() {
        LayoutService layoutService = new LayoutService(cubesListFactoryMock, cubeRepositoryMock, customerRepositoryMock, A_CAFE_NAME, SOME_CUBES_NAME, A_CUBE_SIZE);

        layoutService.reset();

        verify(cubesListFactoryMock, times(2)).create(SOME_CUBES_NAME, A_CUBE_SIZE);
    }

    @Test
    public void whenReset_cubesAreSavedInRepository() {
        when(cubesListFactoryMock.create(SOME_CUBES_NAME, A_CUBE_SIZE)).thenReturn(cubesListMock);
        LayoutService layoutService = new LayoutService(cubesListFactoryMock, cubeRepositoryMock, customerRepositoryMock, A_CAFE_NAME, SOME_CUBES_NAME, A_CUBE_SIZE);

        layoutService.reset();

        verify(cubeRepositoryMock, times(2)).saveCubes(cubesListMock);
    }

    @Test
    public void whenReset_layoutIsCreatedFromNameAndRepositories() {
        LayoutService layoutService = new LayoutService(cubesListFactoryMock, cubeRepositoryMock, customerRepositoryMock, A_CAFE_NAME, SOME_CUBES_NAME, A_CUBE_SIZE);

        layoutService.reset();
        Layout expectedLayout = new Layout(A_CAFE_NAME, cubeRepositoryMock, customerRepositoryMock);

        assertEquals(expectedLayout, layoutService.getLayout());
    }

    @Test
    public void whenUpdateConfiguration_NameIsChanged() {
        LayoutService layoutService = new LayoutService(cubesListFactoryMock, cubeRepositoryMock, customerRepositoryMock, A_CAFE_NAME, SOME_CUBES_NAME, A_CUBE_SIZE);
        String newCafeName = "newCafeName";

        layoutService.updateConfig(newCafeName, A_CUBE_SIZE);

        assertEquals(newCafeName, layoutService.getName());
    }

    @Test
    public void whenUpdateConfiguration_SizeIsChanged() {
        LayoutService layoutService = new LayoutService(cubesListFactoryMock, cubeRepositoryMock, customerRepositoryMock, A_CAFE_NAME, SOME_CUBES_NAME, A_CUBE_SIZE);
        int newCubeSize = 5;

        layoutService.updateConfig(A_CAFE_NAME, newCubeSize);

        assertEquals(newCubeSize, layoutService.getCubeSize());
    }
}
