package com.engagetech.expenses.controllers;

import com.engagetech.expenses.dto.ExpenseRequestDTO;
import com.engagetech.expenses.dto.VatResponseDTO;
import com.engagetech.expenses.entities.Expense;
import com.engagetech.expenses.facades.IAuthenticationFacade;
import com.engagetech.expenses.services.ExpenseService;
import com.engagetech.expenses.utils.VatUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Haytham DAHRI
 */
@RestController
@RequestMapping(path = "/api/v1/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;
    private final IAuthenticationFacade authenticationFacade;
    private final VatUtils vatUtils;

    public ExpenseController(ExpenseService expenseService, IAuthenticationFacade authenticationFacade, VatUtils vatUtils) {
        this.expenseService = expenseService;
        this.authenticationFacade = authenticationFacade;
        this.vatUtils = vatUtils;
    }

    /**
     * <p>Exposed Endpoint to save an expense of the current authenticated user</p>
     *
     * @return ResponseEntity<Expense>
     */
    @ApiOperation(value = "Save an expense of the currency authenticated user")
    @PostMapping(path = "/")
    public ResponseEntity<Expense> saveExpense(@RequestBody ExpenseRequestDTO expenseRequest) {
        // Get Authenticated Username
        final String username = this.authenticationFacade.getAuthentication().getName();
        // Get Expenses
        return ResponseEntity.ok(this.expenseService.saveExpense(username, expenseRequest));
    }

    /**
     * <p>Exposed Endpoint to calculate VAT of an amount</p>
     *
     * @return ResponseEntity<Expense>
     */
    @ApiOperation(value = "Calculate VAT of an amount")
    @GetMapping(path = "/vat")
    public ResponseEntity<VatResponseDTO> calculateExpenseVat(@RequestParam(name = "amount") BigDecimal amount) {
        return ResponseEntity.ok(new VatResponseDTO(this.vatUtils.getVatAmount(amount)));
    }

    /**
     * <p>Exposed Endpoint to retrieve current authenticated user expenses with vat included</p>
     *
     * @return ResponseEntity<ExpenseDTO>
     */
    @ApiOperation(value = "Retrieve authenticated user expenses with VAT included")
    @GetMapping(path = "/users/current")
    public ResponseEntity<List<Expense>> getAuthenticatedUserExpenses() {
        // Get Authenticated Username
        final String username = this.authenticationFacade.getAuthentication().getName();
        // Get Expenses
        return ResponseEntity.ok(this.expenseService.getUserExpenses(username));
    }
}
