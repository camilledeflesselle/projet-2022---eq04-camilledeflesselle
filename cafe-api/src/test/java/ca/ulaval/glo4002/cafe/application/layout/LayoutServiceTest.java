package ca.ulaval.glo4002.cafe.application.layout;

import ca.ulaval.glo4002.cafe.domain.config.Config;
import ca.ulaval.glo4002.cafe.domain.config.ConfigRepository;
import ca.ulaval.glo4002.cafe.domain.cube.Cube;
import ca.ulaval.glo4002.cafe.domain.cube.CubeRepository;
import ca.ulaval.glo4002.cafe.domain.cube.CubesListFactory;
import ca.ulaval.glo4002.cafe.domain.customer.Customer;
import ca.ulaval.glo4002.cafe.domain.customer.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class LayoutServiceTest {
    private static final String A_CAFE_NAME = "Les 4-fées";
    private static final List<String> SOME_CUBES_NAME = new ArrayList<>(List.of("Wanda", "Bloom", "Merryweather", "Tinker Bell"));
    private static final int A_CUBE_SIZE = 4;

    private static CubesListFactory cubesListFactoryMock;
    private static CubeRepository cubeRepositoryMock;
    private static CustomerRepository customerRepositoryMock;
    private static List<Cube> cubesListMock;
    private static LayoutDTOAssembler layoutAssemblerMock;
    private static List<Customer> customerListMock;
    private ConfigRepository configRepositoryMock;

    @BeforeEach
    public void setup() {
        configRepositoryMock = mock(ConfigRepository.class);
        Config config = new Config();
        config.setCubeSize(A_CUBE_SIZE);
        config.setOrganizationName(A_CAFE_NAME);
        config.setCubesNames(SOME_CUBES_NAME);
        when(configRepositoryMock.findConfig()).thenReturn(config);
        cubesListFactoryMock = mock(CubesListFactory.class);
        cubeRepositoryMock = mock(CubeRepository.class);
        customerRepositoryMock = mock(CustomerRepository.class);
        layoutAssemblerMock = mock(LayoutDTOAssembler.class);
        cubesListMock = new ArrayList<>(List.of(mock(Cube.class)));
        customerListMock = new ArrayList<>(List.of(mock(Customer.class)));
    }

    @Test
    public void whenInitialized_cubesAreCreatedFromCubeNamesAndCubeSize() {
        new LayoutService(configRepositoryMock, cubesListFactoryMock, cubeRepositoryMock, customerRepositoryMock, layoutAssemblerMock);

        verify(cubesListFactoryMock).create(SOME_CUBES_NAME, A_CUBE_SIZE);
    }

    @Test
    public void whenInitialized_cubesAreSavedInRepository() {
        when(cubesListFactoryMock.create(SOME_CUBES_NAME, A_CUBE_SIZE)).thenReturn(cubesListMock);
        new LayoutService(configRepositoryMock, cubesListFactoryMock, cubeRepositoryMock, customerRepositoryMock, layoutAssemblerMock);

        verify(cubeRepositoryMock).saveCubes(cubesListMock);
    }

    @Test
    public void whenInitialized_layoutIsCreatedFromNameAndRepositories() {
        LayoutService layoutService = new LayoutService(configRepositoryMock, cubesListFactoryMock, cubeRepositoryMock, customerRepositoryMock, layoutAssemblerMock);
        when(cubesListFactoryMock.create(SOME_CUBES_NAME, A_CUBE_SIZE)).thenReturn(cubesListMock);
        when(cubeRepositoryMock.findAll()).thenReturn(cubesListMock);
        when(customerRepositoryMock.findAll()).thenReturn(customerListMock);

        layoutService.getLayout();

        verify(layoutAssemblerMock).createLayoutDTO(A_CAFE_NAME, cubesListMock, customerListMock);
    }
}
