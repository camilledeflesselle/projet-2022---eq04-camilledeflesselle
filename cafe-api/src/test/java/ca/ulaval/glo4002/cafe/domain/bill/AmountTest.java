package ca.ulaval.glo4002.cafe.domain.bill;

import ca.ulaval.glo4002.cafe.domain.tax.TaxRate;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AmountTest {
    private static final Float AN_UNROUNDED_VALUE = 1.001f;
    private static final Float A_ROUNDED_VALUE = 1.01f;
    private static final Float A_VALUE = 1f;
    private static final Float ANOTHER_VALUE = 2f;

    @Test
    public void whenRoundingAmountToHundredth_thenAmountValueIsRoundedToUpperHundredth() {
        Amount amount = new Amount(AN_UNROUNDED_VALUE);

        Amount roundedAmount = new Amount(amount.roundToHundredth());
        Amount expectedAmount = new Amount(A_ROUNDED_VALUE);

        assertEquals(expectedAmount, roundedAmount);
    }

    @Test
    public void whenAddingAmountToAnotherAmount_thenReturnAmountWithSumOfBothValues() {
        Amount anAmount = new Amount(A_VALUE);
        Amount anotherAmount = new Amount(ANOTHER_VALUE);

        Amount summedAmount = anAmount.add(anotherAmount);
        Amount expectedAmount = new Amount(A_VALUE + ANOTHER_VALUE);

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
