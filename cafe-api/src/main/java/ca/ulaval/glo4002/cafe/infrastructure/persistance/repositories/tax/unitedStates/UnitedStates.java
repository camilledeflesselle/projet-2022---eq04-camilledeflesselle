package ca.ulaval.glo4002.cafe.infrastructure.persistance.repositories.tax.unitedStates;

import ca.ulaval.glo4002.cafe.domain.tax.Area;
import ca.ulaval.glo4002.cafe.domain.tax.TaxRate;

public enum UnitedStates {
    ALABAMA("AL", 4f),
    ARIZONA("AZ", 5.60f),
    CALIFORNIA("CA", 7.25f),
    FLORIDA("FL", 6f),
    MAINE("ME", 5.50f),
    NEW_YORK("NY", 4f),
    TEXAS("TX", 6.25f);

    private final Area stateCode;
    private final TaxRate taxRate;

    UnitedStates(String stateCode, float taxRate) {
        this.stateCode = new Area(stateCode);
        this.taxRate = new TaxRate(taxRate);
    }

    public Area getStateCode() {
        return this.stateCode;
    }

    public TaxRate getTaxRate() {
        return taxRate;
    }
}
