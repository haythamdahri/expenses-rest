package com.engagetech.expenses.services.impl;

import com.engagetech.expenses.dao.ExpenseRepository;
import com.engagetech.expenses.dto.ExpenseDTO;
import com.engagetech.expenses.entities.Expense;
import com.engagetech.expenses.services.ExpenseService;
import com.engagetech.expenses.utils.VatUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Haytham DAHRI
 */
@Service
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final VatUtils vatUtils;

    public ExpenseServiceImpl(ExpenseRepository expenseRepository, VatUtils vatUtils) {
        this.expenseRepository = expenseRepository;
        this.vatUtils = vatUtils;
    }

    @Override
    public Expense saveExpense(Expense expense) {
        return this.expenseRepository.save(expense);
    }

    @Override
    public void deleteExpense(Long id) {
        this.expenseRepository.deleteById(id);
    }

    @Override
    public List<Expense> getExpenses() {
        return this.expenseRepository.findAll();
    }

    @Override
    public List<ExpenseDTO> getUserExpenses(String username) {
        return this.expenseRepository.findByUserUsername(username).stream().map(expense ->
             new ExpenseDTO(expense.getId(), expense.getDate(),
                    vatUtils.calculateAmountWithVat(expense.getAmount()), expense.getReason())
        ).collect(Collectors.toList());
    }
}
