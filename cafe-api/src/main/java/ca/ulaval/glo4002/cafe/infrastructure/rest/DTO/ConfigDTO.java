package ca.ulaval.glo4002.cafe.infrastructure.rest.DTO;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ConfigDTO {
    private String groupReservationMethod;
    private String organizationName;
    private int cubeSize;
    private String country;
    private String province;
    private String state;
    private BigDecimal groupTipRate;

    public ConfigDTO() {}

    public ConfigDTO(String groupReservationMethod, String organizationName, int cubeSize, String country, String province, String state, BigDecimal groupTipRate) {
        this.groupReservationMethod = groupReservationMethod;
        this.organizationName = organizationName;
        this.cubeSize = cubeSize;
        this.country = country;
        this.province = province;
        this.state = state;
        this.groupTipRate = groupTipRate;
    }

    public String getGroupReservationMethod() {
        return this.groupReservationMethod;
    }

    public String getOrganizationName() {
        return this.organizationName;
    }

    public int getCubeSize() {
        return this.cubeSize;
    }

    public String getCountry() {
        return this.country;
    }

    public String getProvince() {
        return this.province;
    }

    public String getState() {
        return this.state;
    }

    public BigDecimal getGroupTipRate() {
        return this.groupTipRate;
    }
}
