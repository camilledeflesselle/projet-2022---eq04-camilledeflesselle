package ca.ulaval.glo4002.cafe.infrastructure.rest.validators.config;

import ca.ulaval.glo4002.cafe.domain.bill.ITaxesRepository;
import ca.ulaval.glo4002.cafe.domain.reservation.reservationStrategy.GroupReservationMethod;
import ca.ulaval.glo4002.cafe.infrastructure.rest.DTO.ConfigDTO;
import ca.ulaval.glo4002.cafe.domain.reservation.InvalidGroupReservationMethodException;
import jakarta.ws.rs.BadRequestException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Objects;

public class ConfigValidator {
    private final ITaxesRepository taxesRepository;
    private GroupReservationMethod reservationMethod;

    public ConfigValidator(ITaxesRepository taxesRepository) {
        this.taxesRepository = taxesRepository;
    }

    public void validateConfig(ConfigDTO configDTO) {
        if (configDTO == null) {
            throw new BadRequestException();
        }
        if (isOneConfigAttributeNull(configDTO)) {
            throw new BadRequestException();
        }
        if (!isGroupReservationMethodValid(configDTO.getGroupReservationMethod())) {
            throw new InvalidGroupReservationMethodException();
        }
        if (!isCubeSizeValid(configDTO.getCubeSize())) {
            throw new BadRequestException();
        }
        if (!isOrganizationNameValid(configDTO.getOrganizationName())) {
            throw new BadRequestException();
        }
        if (!isCountryValid(configDTO.getCountry(), configDTO.getProvince(), configDTO.getState())) {
            throw new InvalidCountryException();
        }
        if (!isGroupTipRateValid(configDTO.getGroupTipRate())) {
            throw new InvalidGroupTipRateException();
        }
    }

    public boolean isOneConfigAttributeNull(ConfigDTO configDTO) {
        return configDTO.getGroupReservationMethod() == null
                || configDTO.getOrganizationName() == null
                || configDTO.getCubeSize() == 0
                || configDTO.getCountry() == null
                || configDTO.getProvince() == null || configDTO.getState() == null
                || configDTO.getGroupTipRate() == null;
    }

    public boolean isGroupReservationMethodValid(String groupReservationMethod) {
        return Arrays.stream(
                GroupReservationMethod.values()).anyMatch(e -> {
                    boolean reservationIsValid = e.label.equals(groupReservationMethod);
                    if (reservationIsValid) {
                        reservationMethod = e;
                    }
                    return reservationIsValid;
                }
        );
    }

    public boolean isOrganizationNameValid(String organizationName) {
        return organizationName.length() > 0;
    }

    public boolean isCubeSizeValid(int cubeSize) {
        return cubeSize > 0;
    }

    public boolean isCountryValid(String country, String province, String state) {
        if (Objects.equals(country, "None")) return true;

        if (!this.taxesRepository.findCountries().contains(country)) return false;

        String option = this.taxesRepository.findCalculationTypeByCountry(country);

        switch (option) {
            case "province":
                return this.taxesRepository.findTerritoriesNamesByCountry(country).contains(province);
            case "state":
                return this.taxesRepository.findTerritoriesNamesByCountry(country).contains(state);
        }
        return true;
    }

    public GroupReservationMethod getGroupReservationMethod() {
        return this.reservationMethod;
    }

    public boolean isGroupTipRateValid(BigDecimal groupTipRate) {
        return groupTipRate.compareTo(BigDecimal.ZERO) >= 0
                && groupTipRate.compareTo(BigDecimal.valueOf(100)) <= 0;
    }
}
