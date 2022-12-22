package ca.ulaval.glo4002.cafe.ui.rest.assemblers.config;

import ca.ulaval.glo4002.cafe.domain.bill.TipRate;
import ca.ulaval.glo4002.cafe.domain.config.Config;
import ca.ulaval.glo4002.cafe.domain.reservation.InvalidGroupReservationMethodException;
import ca.ulaval.glo4002.cafe.domain.reservation.reservationStrategy.GroupReservationStrategy;
import ca.ulaval.glo4002.cafe.domain.tax.Area;
import ca.ulaval.glo4002.cafe.domain.tax.ITaxesRepository;
import ca.ulaval.glo4002.cafe.domain.tax.TaxRate;
import ca.ulaval.glo4002.cafe.infrastructure.persistance.repositories.tax.Country;
import ca.ulaval.glo4002.cafe.ui.rest.DTO.ConfigDTO;
import jakarta.ws.rs.BadRequestException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ConfigAssembler {
    private final ITaxesRepository taxesRepository;
    private final Config config;

    public ConfigAssembler(ITaxesRepository taxesRepository) {
        this.taxesRepository = taxesRepository;
        this.config = new Config();
    }

    public void validateConfig(ConfigDTO configDTO) {
        if (configDTO == null) {
            throw new BadRequestException();
        }
        if (checkIfOneOfNeededAttributeIsNull(configDTO)) {
            throw new BadRequestException();
        }
        if (!validateGroupReservationMethod(configDTO.getGroupReservationMethod())) {
            throw new InvalidGroupReservationMethodException();
        }
        if (!validateCubeSize(configDTO.getCubeSize())) {
            throw new BadRequestException();
        }
        if (!validateOrganizationName(configDTO.getOrganizationName())) {
            throw new BadRequestException();
        }
        if (!validateCountry(configDTO.getCountry(), configDTO.getProvince(), configDTO.getState())) {
            throw new InvalidCountryException();
        }
        if (!isGroupTipRateValid(configDTO.getGroupTipRate())) {
            throw new InvalidGroupTipRateException();
        }
    }

    public Config assembleConfig(ConfigDTO configDTO) {
        this.validateConfig(configDTO);
        this.config.setCubeSize(configDTO.getCubeSize());
        this.config.setOrganizationName(configDTO.getOrganizationName());
        this.config.setGroupTipRate(new TipRate(configDTO.getGroupTipRate().doubleValue()));
        List<String> cubesNames = new ArrayList<>(List.of("Wanda", "Bloom", "Merryweather", "Tinker Bell"));
        this.config.setCubesNames(cubesNames);
        return this.config;
    }

    public boolean checkIfOneOfNeededAttributeIsNull(ConfigDTO configDTO) {
        return configDTO.getGroupReservationMethod() == null
                || configDTO.getOrganizationName() == null
                || configDTO.getCubeSize() == 0
                || configDTO.getCountry() == null
                || configDTO.getProvince() == null || configDTO.getState() == null
                || configDTO.getGroupTipRate() == null;
    }

    public boolean validateGroupReservationMethod(String groupReservationMethod) {
        return Arrays.stream(
                GroupReservationStrategy.values()).anyMatch(method -> {
                    boolean reservationIsValid = method.label.equals(groupReservationMethod);
                    if (reservationIsValid) {
                        this.config.setGroupReservationMethod(method);
                    }
                    return reservationIsValid;
                }
        );
    }

    public boolean validateOrganizationName(String organizationName) {
        return organizationName.length() > 0;
    }

    public boolean validateCubeSize(int cubeSize) {
        return cubeSize > 0;
    }

    public boolean validateCountry(String country, String province, String state) {
        Country countryId = Country.toEnum(country);
        if (countryId == null) return false;
        Area area = null;
        switch (countryId) {
            case CANADA:
                area = new Area(province);
                break;
            case UNITED_STATES:
                area = new Area(state);
                break;
            case CL, NONE:
                break;
            default:
                return false;
        }
        TaxRate taxRate = taxesRepository.findTaxRate(countryId.getCountryCode(), area);
        if (taxRate == null) {
            return false;
        }
        this.config.setTaxRate(taxRate);
        return true;
    }

    public boolean isGroupTipRateValid(BigDecimal groupTipRate) {
        return groupTipRate.compareTo(BigDecimal.ZERO) >= 0
                && groupTipRate.compareTo(BigDecimal.valueOf(100)) <= 0;
    }
}
