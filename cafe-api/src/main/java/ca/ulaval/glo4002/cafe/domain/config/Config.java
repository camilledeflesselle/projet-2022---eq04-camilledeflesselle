package ca.ulaval.glo4002.cafe.domain.config;

import ca.ulaval.glo4002.cafe.domain.bill.TipRate;
import ca.ulaval.glo4002.cafe.domain.reservation.reservationStrategy.GroupReservationStrategy;
import ca.ulaval.glo4002.cafe.domain.tax.TaxRate;

import java.util.List;

public class Config {
    private List<String> cubeNames;
    private String name;
    private int cubeSize;
    private TaxRate taxRate;
    private TipRate groupTipRate;
    private GroupReservationStrategy reservationMethod;

    public Config(String name, List<String> cubeNames, int cubeSize, TaxRate taxRate, TipRate groupTipRate, GroupReservationStrategy reservationMethod) {
        this.name = name;
        this.cubeSize = cubeSize;
        this.taxRate = taxRate;
        this.groupTipRate = groupTipRate;
        this.cubeNames = cubeNames;
        this.reservationMethod = reservationMethod;
    }

    public Config() {
    }

    public String getName() {
        return name;
    }

    public int getCubeSize() {
        return cubeSize;
    }

    public GroupReservationStrategy getReservationMethod() {
        return reservationMethod;
    }

    public TaxRate getTaxRate() {
        return this.taxRate;
    }

    public TipRate getGroupTipRate() {
        return this.groupTipRate;
    }

    public List<String> getCubesNames() {
        return this.cubeNames;
    }

    public void setGroupReservationMethod(GroupReservationStrategy method) {
        this.reservationMethod = method;
    }

    public void setCubeSize(int cubeSize) {
        this.cubeSize = cubeSize;
    }

    public void setOrganizationName(String organizationName) {
        this.name = organizationName;
    }

    public void setTaxRate(TaxRate taxRate) {
        this.taxRate = taxRate;
    }

    public void setGroupTipRate(TipRate groupTipRate) {
        this.groupTipRate = groupTipRate;
    }

    public void setCubesNames(List<String> someCubesName) {
        this.cubeNames = someCubesName;
    }
}
