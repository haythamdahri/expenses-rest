package com.engagetech.expenses.clients;

/**
 * @author Haytham DAHRI
 */

import com.engagetech.expenses.dto.CurrencyResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "currency-client", url = "${com.engage.currency.uri}")
public interface CurrencyClient {

    @GetMapping(path = "/latest", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<CurrencyResponseDTO> getCurrencyRates(@RequestParam(name = "base") String baseCurrency);

}
