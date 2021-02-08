package com.engagetech.expenses.controllers;

import com.engagetech.expenses.dto.ExpenseDTO;
import com.engagetech.expenses.facades.IAuthenticationFacade;
import com.engagetech.expenses.services.ExpenseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Haytham DAHRI
 */
@RestController
@RequestMapping(path = "/api/v1/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;
    private final IAuthenticationFacade authenticationFacade;

    public ExpenseController(ExpenseService expenseService, IAuthenticationFacade authenticationFacade) {
        this.expenseService = expenseService;
        this.authenticationFacade = authenticationFacade;
    }

    /**
     * <p>Exposed Endpoint to retrieve current authenticated user expenses with vat included</p>
     * @return ResponseEntity<ExpenseDTO>
     */
    @GetMapping(path = "/users/current")
    public ResponseEntity<List<ExpenseDTO>> getAuthenticatedUserExpenses() {
        // Get Authenticated Username
        final String username = this.authenticationFacade.getAuthentication().getName();
        // Get Expenses
        return ResponseEntity.ok(this.expenseService.getUserExpenses(username));
    }
}
