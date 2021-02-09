package com.engagetech.expenses.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author Haytham DAHRI
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VatResponseDTO implements Serializable {
    private static final long serialVersionUID = -3638877364007530461L;

    private BigDecimal amount;

}
