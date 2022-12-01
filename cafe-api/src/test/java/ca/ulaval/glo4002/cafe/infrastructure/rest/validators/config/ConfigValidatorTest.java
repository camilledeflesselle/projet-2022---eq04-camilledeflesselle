package ca.ulaval.glo4002.cafe.infrastructure.rest.validators.config;

import ca.ulaval.glo4002.cafe.domain.bill.ITaxesRepository;
import ca.ulaval.glo4002.cafe.domain.reservation.InvalidGroupReservationMethodException;
import ca.ulaval.glo4002.cafe.domain.reservation.reservationStrategy.GroupReservationMethod;
import ca.ulaval.glo4002.cafe.infrastructure.persistance.repositories.TaxesRepositoryInMemory;
import ca.ulaval.glo4002.cafe.infrastructure.rest.DTO.ConfigDTO;
import jakarta.ws.rs.BadRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ConfigValidatorTest {
    private static final String AN_ORGANIZATION_NAME = "Organisation name";
    private static final String AN_EMPTY_ORGANIZATION_NAME = "";
    private static final String A_NON_EXISTING_GROUP_RESERVATION_METHOD = "Invalid group reservation method";
    private static final int A_CUBE_SIZE = 4;
    private static final int A_NEGATIVE_CUBE_SIZE = -15;
    private static final String A_COUNTRY = "CA";
    private static final String A_PROVINCE = "QC";
    private static final String AN_EMPTY_STATE = "";
    private static final BigDecimal A_TIP_RATE = new BigDecimal(Float.toString(0.015f));

    private ConfigValidator configValidator;

    @BeforeEach
    public void setupConfigValidator() {
        ITaxesRepository taxesRepository = new TaxesRepositoryInMemory();
        this.configValidator = new ConfigValidator(taxesRepository);
    }

    @Test
    public void whenConfigDTOisNll_thenThrowBadRequestException() {
        assertThrows(BadRequestException.class, () -> configValidator.validateConfig(null));
    }

    @Test
    public void whenConfigDTOHasOneNullAttribute_thenNotValidateConfig() {
        ConfigDTO configDTO = new ConfigDTO(
                null, AN_ORGANIZATION_NAME, A_CUBE_SIZE, A_COUNTRY, A_PROVINCE, AN_EMPTY_STATE, A_TIP_RATE
        );

        assertThrows(BadRequestException.class,
                () -> this.configValidator.validateConfig(configDTO));
    }

    @Test
    public void givenANonExistingReservationMethod_whenValidatingConfig_thenNotValidateConfig() {
        ConfigDTO configDTO = new ConfigDTO(
                A_NON_EXISTING_GROUP_RESERVATION_METHOD, AN_ORGANIZATION_NAME, A_CUBE_SIZE, A_COUNTRY, A_PROVINCE, AN_EMPTY_STATE, A_TIP_RATE
        );

        assertThrows(InvalidGroupReservationMethodException.class,
                () -> this.configValidator.validateConfig(configDTO));
    }

    @Test
    public void givenANegativeCubeSize_whenValidatingConfig_thenNotValidateConfig() {
        ConfigDTO configDTO = new ConfigDTO(
                GroupReservationMethod.DEFAULT.label, AN_ORGANIZATION_NAME, A_NEGATIVE_CUBE_SIZE, A_COUNTRY, A_PROVINCE, AN_EMPTY_STATE, A_TIP_RATE
        );
        assertThrows(BadRequestException.class,
                () -> this.configValidator.validateConfig(configDTO));
    }

    @Test
    public void givenAnEmptyOrganizationName_whenValidatingConfig_thenNotValidateConfig() {
        ConfigDTO configDTO = new ConfigDTO(
                GroupReservationMethod.DEFAULT.label, AN_EMPTY_ORGANIZATION_NAME, A_CUBE_SIZE, A_COUNTRY, A_PROVINCE, AN_EMPTY_STATE, A_TIP_RATE
        );
        assertThrows(BadRequestException.class,
                () -> this.configValidator.validateConfig(configDTO));
    }

    @Test
    public void givenAnInvalidCountry_whenValidatingConfig_thenThrowInvalidCountryException() {
        ConfigDTO configDTO = new ConfigDTO(
                GroupReservationMethod.DEFAULT.label, AN_ORGANIZATION_NAME, A_CUBE_SIZE,
            "INVALID_COUNTRY", A_PROVINCE, AN_EMPTY_STATE, A_TIP_RATE
        );
        assertThrows(InvalidCountryException.class,
                () -> this.configValidator.validateConfig(configDTO));
    }

    @Test
    public void givenAnValidCountryWithoutProvinceOrState_whenCheckCountryConfigIsValid_thenReturnTrue() {
        String aValidCountryWithoutProvinceOrState = "CL";

        boolean isValid = this.configValidator.isCountryValid(aValidCountryWithoutProvinceOrState, null, null);

        assertTrue(isValid);
    }

    @Test
    public void givenAValidCountryWithProvince_whenCheckCountryConfigIsValid_thenReturnTrue() {
        String validCountryWithProvince = "CA";
        String validProvinceCorrespondingToCountry = "QC";

        boolean isValid = this.configValidator.isCountryValid(validCountryWithProvince, validProvinceCorrespondingToCountry, null);

        assertTrue(isValid);
    }

    @Test
    public void givenAValidCountryWithState_whenCheckCountryConfigIsValid_thenReturnTrue() {
        String validCountryWithState = "US";
        String validStateCorrespondingToCountry = "CA";

        boolean isValid = this.configValidator.isCountryValid(validCountryWithState, null, validStateCorrespondingToCountry);

        assertTrue(isValid);
    }

    @Test
    public void givenAnNegativeGroupTipRate_whenValidatingConfig_thenThrowInvalidGroupTipRateException() {
        String invalidNegativeGroupTipRate = "-15";
        ConfigDTO configDTO = new ConfigDTO(
                GroupReservationMethod.DEFAULT.label, AN_ORGANIZATION_NAME, A_CUBE_SIZE,
                A_COUNTRY, A_PROVINCE, AN_EMPTY_STATE, new BigDecimal(invalidNegativeGroupTipRate)
        );

        assertThrows(InvalidGroupTipRateException.class,
                () -> this.configValidator.validateConfig(configDTO));
    }

    @Test
    public void givenAnValidGroupTipRate_whenValidatingConfig_thenNotThrowInvalidGroupTipRateException() {
        String invalidSuperiorHundredGroupTipRate = "150";
        ConfigDTO configDTO = new ConfigDTO(
                GroupReservationMethod.DEFAULT.label, AN_ORGANIZATION_NAME, A_CUBE_SIZE,
                A_COUNTRY, A_PROVINCE, AN_EMPTY_STATE, new BigDecimal(invalidSuperiorHundredGroupTipRate)
        );

        assertThrows(InvalidGroupTipRateException.class,
                () -> this.configValidator.validateConfig(configDTO));
    }
}
