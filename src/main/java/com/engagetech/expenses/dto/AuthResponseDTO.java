package com.engagetech.expenses.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Haytham DAHRI
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponseDTO implements Serializable {
    private static final long serialVersionUID = -7345357819996696018L;

    private String token;

}
