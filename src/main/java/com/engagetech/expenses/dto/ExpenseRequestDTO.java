package com.engagetech.expenses.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Haytham DAHRI
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExpenseRequestDTO implements Serializable {
    private static final long serialVersionUID = -4927026346259924383L;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date date;

    private BigDecimal amount;

    private String currency;

    private String reason;

}
