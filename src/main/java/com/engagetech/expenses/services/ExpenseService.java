package com.engagetech.expenses.services;

import com.engagetech.expenses.dto.ExpenseDTO;
import com.engagetech.expenses.entities.Expense;

import java.util.List;

/**
 * @author Haytham DAHRI
 */
public interface ExpenseService {

    Expense saveExpense(Expense expense);

    void deleteExpense(Long id);

    List<Expense> getExpenses();

    List<ExpenseDTO> getUserExpenses(String username);

}
