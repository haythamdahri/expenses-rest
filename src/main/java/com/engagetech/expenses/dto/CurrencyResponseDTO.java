package com.engagetech.expenses.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

/**
 * @author Haytham DAHRI
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyResponseDTO implements Serializable {

    private static final long serialVersionUID = 573398314836974345L;

    private String base;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date date;

    private Map<String, BigDecimal> rate;

}
