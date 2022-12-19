package ca.ulaval.glo4002.cafe.infrastructure.persistance.repositories.tax.canadian;

import ca.ulaval.glo4002.cafe.domain.tax.Area;
import ca.ulaval.glo4002.cafe.domain.tax.TaxRate;

public enum CanadianProvinces {
    ALBERTA("AB", 0f),
    BRITISH_COLUMBIA("BC", 7f),
    MANITOBA("MB", 7f),
    NEW_BRUNSWICK("NB", 10f),
    NEWFOUNDLAND_AND_LABRADOR("NL", 10f),
    NOVA_SCOTIA("NS", 10f),
    NORTHWEST_TERRITORIES("NT", 0f),
    NUNAVUT("NU", 0f),
    ONTARIO("ON", 8f),
    PRINCE_EDWARD_ISLAND("PE", 10f),
    QUEBEC("QC", 9.975f),
    SASKATCHEWAN("SK", 6f),
    YUKON("YT", 0f);
    private final Area provinceCode;
    private final TaxRate taxRate;

    CanadianProvinces(String provinceCode, float taxRate) {
        this.provinceCode = new Area(provinceCode);
        this.taxRate = new TaxRate(taxRate);
    }

    public Area getProvinceCode() {
        return this.provinceCode;
    }

    public TaxRate getTaxRate() {
        return taxRate;
    }
}
