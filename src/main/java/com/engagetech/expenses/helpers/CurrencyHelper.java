package com.engagetech.expenses.helpers;

import com.engagetech.expenses.clients.CurrencyClient;
import com.engagetech.expenses.dto.CurrencyResponseDTO;
import com.engagetech.expenses.exceptions.BusinessException;
import com.engagetech.expenses.exceptions.TechnicalException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Objects;

/**
 * @author Haytham DAHRI
 */
@Component
public class CurrencyHelper {

    private final CurrencyClient currencyClient;

    public CurrencyHelper(CurrencyClient currencyClient) {
        this.currencyClient = currencyClient;
    }

    public BigDecimal convertToPound(BigDecimal amount, String sourceCurrency, String conversionCurrency) {
        ResponseEntity<CurrencyResponseDTO> currencyResponse = this.currencyClient.getCurrencyRates(sourceCurrency);
        if( !currencyResponse.getStatusCode().is2xxSuccessful() && !currencyResponse.hasBody() ) {
            throw new TechnicalException();
        }
        Map<String, BigDecimal> rates = Objects.requireNonNull(currencyResponse.getBody()).getRate();
        if( !rates.containsKey(conversionCurrency) ) {
            throw new BusinessException("Invalid currency!");
        }
        return rates.get(conversionCurrency).multiply(amount);
    }

}
