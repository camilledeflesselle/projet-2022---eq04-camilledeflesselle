package ca.ulaval.glo4002.cafe.ui.rest.assemblers.validators.config;

import ca.ulaval.glo4002.cafe.domain.config.Config;
import ca.ulaval.glo4002.cafe.domain.reservation.InvalidGroupReservationMethodException;
import ca.ulaval.glo4002.cafe.domain.reservation.reservationStrategy.GroupReservationStrategy;
import ca.ulaval.glo4002.cafe.domain.tax.TaxRate;
import ca.ulaval.glo4002.cafe.domain.tax.TaxRepository;
import ca.ulaval.glo4002.cafe.infrastructure.persistance.repositories.tax.Country;
import ca.ulaval.glo4002.cafe.infrastructure.persistance.repositories.tax.TaxRepositoryInMemory;
import ca.ulaval.glo4002.cafe.infrastructure.persistance.repositories.tax.canada.CanadaProvinces;
import ca.ulaval.glo4002.cafe.ui.rest.DTO.ConfigDTO;
import ca.ulaval.glo4002.cafe.ui.rest.assemblers.config.ConfigAssembler;
import ca.ulaval.glo4002.cafe.ui.rest.assemblers.config.InvalidCountryException;
import ca.ulaval.glo4002.cafe.ui.rest.assemblers.config.InvalidGroupTipRateException;
import jakarta.ws.rs.BadRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ConfigAssemblerTest {
    private static final String AN_ORGANIZATION_NAME = "Organisation name";
    private static final String AN_EMPTY_ORGANIZATION_NAME = "";
    private static final String A_NON_EXISTING_GROUP_RESERVATION_METHOD = "Invalid group reservation method";
    private static final int A_CUBE_SIZE = 4;
    private static final int A_NEGATIVE_CUBE_SIZE = -15;
    private static final String A_COUNTRY = "CA";
    private static final String A_PROVINCE = "QC";
    private static final String AN_EMPTY_STATE = "";
    private static final BigDecimal A_TIP_RATE = new BigDecimal(Float.toString(0.015f));

    private ConfigAssembler configAssembler;
    private TaxRepository taxRepository;

    @BeforeEach
    public void setupConfigValidator() {
        taxRepository = new TaxRepositoryInMemory();
        configAssembler = new ConfigAssembler(taxRepository);
    }

    @Test
    public void whenConfigDTOisNull_thenThrowBadRequestException() {
        assertThrows(BadRequestException.class, () -> configAssembler.validateConfig(null));
    }

    @Test
    public void whenConfigDTOHasOneNullAttribute_thenNotValidateConfig() {
        ConfigDTO configDTO = new ConfigDTO(
                null, AN_ORGANIZATION_NAME, A_CUBE_SIZE, A_COUNTRY, A_PROVINCE, AN_EMPTY_STATE, A_TIP_RATE
        );

        assertThrows(BadRequestException.class,
                () -> configAssembler.validateConfig(configDTO));
    }

    @Test
    public void givenANonExistingReservationMethod_whenAssembleConfigDTO_thenNotValidateConfig() {
        ConfigDTO configDTO = new ConfigDTO(
                A_NON_EXISTING_GROUP_RESERVATION_METHOD, AN_ORGANIZATION_NAME, A_CUBE_SIZE, A_COUNTRY, A_PROVINCE, AN_EMPTY_STATE, A_TIP_RATE
        );

        assertThrows(InvalidGroupReservationMethodException.class,
                () -> configAssembler.assembleToConfig(configDTO));
    }

    @Test
    public void givenANegativeCubeSize_whenAssembleConfigDTO_thenNotValidateConfig() {
        ConfigDTO configDTO = new ConfigDTO(
                GroupReservationStrategy.Default.label, AN_ORGANIZATION_NAME, A_NEGATIVE_CUBE_SIZE, A_COUNTRY, A_PROVINCE, AN_EMPTY_STATE, A_TIP_RATE
        );
        assertThrows(BadRequestException.class,
                () -> configAssembler.assembleToConfig(configDTO));
    }

    @Test
    public void givenAnEmptyOrganizationName_whenToConfig_thenNotValidateConfig() {
        ConfigDTO configDTO = new ConfigDTO(
                GroupReservationStrategy.Default.label, AN_EMPTY_ORGANIZATION_NAME, A_CUBE_SIZE, A_COUNTRY, A_PROVINCE, AN_EMPTY_STATE, A_TIP_RATE
        );
        assertThrows(BadRequestException.class,
                () -> configAssembler.assembleToConfig(configDTO));
    }

    @Test
    public void givenAnInvalidCountry_whenToConfig_thenThrowInvalidCountryException() {
        ConfigDTO configDTO = new ConfigDTO(
                GroupReservationStrategy.Default.label, AN_ORGANIZATION_NAME, A_CUBE_SIZE,
                "INVALID_COUNTRY", A_PROVINCE, AN_EMPTY_STATE, A_TIP_RATE
        );
        assertThrows(InvalidCountryException.class,
                () -> configAssembler.assembleToConfig(configDTO));
    }

    @Test
    public void givenAnValidCountryWithoutProvinceOrState_whenCheckCountryConfigIsValid_thenReturnTrue() {
        String aValidCountryWithoutProvinceOrState = "CL";

        boolean isValid = configAssembler.validateCountry(aValidCountryWithoutProvinceOrState, null, null);

        assertTrue(isValid);
    }

    @Test
    public void givenAValidCountryWithProvince_whenCheckCountryConfigIsValid_thenReturnTrue() {
        String validCountryWithProvince = "CA";
        String validProvinceCorrespondingToCountry = "QC";

        boolean isValid = configAssembler.validateCountry(validCountryWithProvince, validProvinceCorrespondingToCountry, null);

        assertTrue(isValid);
    }

    @Test
    public void givenAValidCountryWithState_whenCheckCountryConfigIsValid_thenReturnTrue() {
        String validCountryWithState = "US";
        String validStateCorrespondingToCountry = "CA";

        boolean isValid = configAssembler.validateCountry(validCountryWithState, null, validStateCorrespondingToCountry);

        assertTrue(isValid);
    }

    @Test
    public void givenAnNegativeGroupTipRate_whenToConfig_thenThrowInvalidGroupTipRateException() {
        String invalidNegativeGroupTipRate = "-15";
        ConfigDTO configDTO = new ConfigDTO(
                GroupReservationStrategy.Default.label, AN_ORGANIZATION_NAME, A_CUBE_SIZE,
                A_COUNTRY, A_PROVINCE, AN_EMPTY_STATE, new BigDecimal(invalidNegativeGroupTipRate)
        );

        assertThrows(InvalidGroupTipRateException.class,
                () -> configAssembler.assembleToConfig(configDTO));
    }

    @Test
    public void givenAnValidGroupTipRate_whenToConfig_thenNotThrowInvalidGroupTipRateException() {
        String invalidSuperiorHundredGroupTipRate = "150";
        ConfigDTO configDTO = new ConfigDTO(
                GroupReservationStrategy.Default.label, AN_ORGANIZATION_NAME, A_CUBE_SIZE,
                A_COUNTRY, A_PROVINCE, AN_EMPTY_STATE, new BigDecimal(invalidSuperiorHundredGroupTipRate)
        );

        assertThrows(InvalidGroupTipRateException.class,
                () -> configAssembler.assembleToConfig(configDTO));
    }

    @Test
    public void givenNoneCountryAndEmptyStateAndProvince_whenToConfig_thenReturnConfigNotNull() {
        ConfigDTO configDTO = new ConfigDTO(
                GroupReservationStrategy.Default.label, AN_ORGANIZATION_NAME, A_CUBE_SIZE,
                "None", "", "", A_TIP_RATE
        );

        Config config = configAssembler.assembleToConfig(configDTO);

        assertNotNull(config);
    }

    @Test
    public void givenNoneCountryAndEmptyStateAndProvince_whenToConfig_thenReturnConfigWithName() {
        ConfigDTO configDTO = new ConfigDTO(
                GroupReservationStrategy.Default.label, AN_ORGANIZATION_NAME, A_CUBE_SIZE,
                "None", "", "", A_TIP_RATE
        );

        Config config = configAssembler.assembleToConfig(configDTO);

        assertNotNull(config);

        assertNotNull(config.getName());
    }

    @Test
    public void givenNoneCountryAndEmptyStateAndProvince_whenToConfig_thenReturnConfigWithTaxRate() {
        ConfigDTO configDTO = new ConfigDTO(
                GroupReservationStrategy.Default.label, AN_ORGANIZATION_NAME, A_CUBE_SIZE,
                Country.None.getCountryCode().getName(), "", "", A_TIP_RATE
        );

        Config config = configAssembler.assembleToConfig(configDTO);

        assertEquals(0, config.getTaxRate().getRate().doubleValue());
    }

    @Test
    public void givenNoneCountryAndEmptyStateAndProvince_whenToConfig_thenReturnConfigWithCubesNames() {
        ConfigDTO configDTO = new ConfigDTO(
                GroupReservationStrategy.Default.label, AN_ORGANIZATION_NAME, A_CUBE_SIZE,
                "None", "", "", A_TIP_RATE
        );

        Config config = configAssembler.assembleToConfig(configDTO);

        assertNotNull(config.getCubesNames());
    }

    @Test
    public void givenNoneCountryAndEmptyStateAndProvince_whenToConfig_thenReturnConfigWithNotNullReservationMethod() {
        ConfigDTO configDTO = new ConfigDTO(
                GroupReservationStrategy.Default.label, AN_ORGANIZATION_NAME, A_CUBE_SIZE,
                "None", "", "", A_TIP_RATE
        );

        Config config = configAssembler.assembleToConfig(configDTO);

        assertEquals(GroupReservationStrategy.Default, config.getReservationMethod());
    }

    @Test
    public void givenNoneCountryAndEmptyStateAndProvince_whenToConfig_thenReturnConfigWithNotNullGroupTipRate() {
        ConfigDTO configDTO = new ConfigDTO(
                GroupReservationStrategy.Default.label, AN_ORGANIZATION_NAME, A_CUBE_SIZE,
                "None", "", "", A_TIP_RATE
        );

        Config config = configAssembler.assembleToConfig(configDTO);

        assertEquals(A_TIP_RATE.doubleValue(), config.getGroupTipRate().getRate().doubleValue());
    }

    @Test
    void whenAssembleConfigWithCountryAndArea_thenFindTaxRateInTaxesRepositoryFromCountryAndArea() {
        taxRepository = mock(TaxRepository.class);
        when(taxRepository.findTaxRate(any(), any())).thenReturn(new TaxRate(0.15f));
        configAssembler = new ConfigAssembler(taxRepository);
        ConfigDTO configDTO = new ConfigDTO(
                GroupReservationStrategy.Default.label, AN_ORGANIZATION_NAME, A_CUBE_SIZE,
                "CA", "QC", "", A_TIP_RATE
        );

        configAssembler.assembleToConfig(configDTO);

        verify(taxRepository).findTaxRate(Country.Canada.getCountryCode(), CanadaProvinces.QUEBEC.getProvinceCode());
    }

}
