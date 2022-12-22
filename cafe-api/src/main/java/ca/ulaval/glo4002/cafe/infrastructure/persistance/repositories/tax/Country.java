package ca.ulaval.glo4002.cafe.infrastructure.persistance.repositories.tax;

import ca.ulaval.glo4002.cafe.domain.tax.Area;
import ca.ulaval.glo4002.cafe.domain.tax.TaxRate;

public enum Country {
    Canada("CA", 5f, true),
    UnitedStates("US", 0f, true),
    Cl("CL", 19f, false),
    None("None", 0f, false);

    private final Area countryCode;
    private final TaxRate taxRate;
    private final boolean hasAreas;

    Country(String countryCode, float taxRate, boolean hasAreas) {
        this.countryCode = new Area(countryCode);
        this.taxRate = new TaxRate(taxRate);
        this.hasAreas = hasAreas;
    }

    public static Country toEnum(String country) {
        for (Country c : Country.values()) {
            if (c.countryCode.equals(new Area(country))) {
                return c;
            }
        }
        return null;
    }

    public Area getCountryCode() {
        return countryCode;
    }

    public TaxRate getTaxRate() {
        return taxRate;
    }

    public boolean hasAreas() {
        return hasAreas;
    }
}
