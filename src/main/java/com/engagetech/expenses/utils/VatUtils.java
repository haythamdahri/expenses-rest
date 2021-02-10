package com.engagetech.expenses.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author Haytham DAHRI
 */
@Component
public class VatUtils {

    private final BigDecimal ONE_HUNDRED = BigDecimal.valueOf(100);

    @Value("${vat.percentage}")
    private double vatPercentage;

    public BigDecimal calculateAmountWithVat(BigDecimal amount) {
        return amount.add((amount.multiply(BigDecimal.valueOf(this.vatPercentage))).divide(ONE_HUNDRED, RoundingMode.DOWN));
    }

    public BigDecimal getVatAmount(BigDecimal amount) {
        return this.calculateAmountWithVat(amount).subtract(amount);
    }

}
