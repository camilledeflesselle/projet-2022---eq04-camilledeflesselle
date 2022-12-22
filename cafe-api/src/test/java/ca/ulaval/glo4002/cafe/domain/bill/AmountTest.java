package ca.ulaval.glo4002.cafe.domain.bill;

import ca.ulaval.glo4002.cafe.domain.tax.TaxRate;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AmountTest {

    @Test
    public void whenRoundingAmountToHundredth_thenAmountValueIsRoundedToUpperHundredth() {
        Amount amount = new Amount(1.001f);
        assertEquals(1.01f, amount.roundToHundredth());
    }

    @Test
    public void whenAddingAmountToAnotherAmount_thenReturnAmountWithSumOfBothValues() {
        Amount anAmount = new Amount(1f);
        Amount anotherAmount = new Amount(2f);

        Amount summedAmount = anAmount.add(anotherAmount);
        Amount expectedAmount = new Amount(3f);

        assertEquals(expectedAmount, summedAmount);
    }

    @Test
    public void whenApplyingTaxRateToAmount_thenReturnTheTaxPortionOfTheAmount() {
        Amount amount = new Amount(10f);
        TaxRate taxRate = new TaxRate(50f);

        Amount taxAmount = amount.applyRate(taxRate);
        Amount expectedTaxAmount = new Amount(5f);

        assertEquals(expectedTaxAmount, taxAmount);
    }

    @Test
    public void whenApplyingTipRateToAmount_thenReturnTheTipPortionOfTheAmount() {
        Amount amount = new Amount(10f);
        TipRate tipRate = new TipRate(50f);

        Amount taxAmount = amount.applyRate(tipRate);
        Amount expectedTaxAmount = new Amount(5f);

        assertEquals(expectedTaxAmount, taxAmount);
    }
}
