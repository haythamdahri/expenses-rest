package com.engagetech.expenses.services.impl;

import com.engagetech.expenses.constants.CurrencyConstants;
import com.engagetech.expenses.dao.ExpenseRepository;
import com.engagetech.expenses.dto.ExpenseDTO;
import com.engagetech.expenses.dto.ExpenseRequestDTO;
import com.engagetech.expenses.entities.Expense;
import com.engagetech.expenses.entities.User;
import com.engagetech.expenses.helpers.CurrencyHelper;
import com.engagetech.expenses.mappers.ExpenseMapper;
import com.engagetech.expenses.services.ExpenseService;
import com.engagetech.expenses.services.UserService;
import com.engagetech.expenses.utils.VatUtils;
import org.apache.commons.lang3.StringUtils;
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
    private final UserService userService;
    private final ExpenseMapper expenseMapper;
    private final CurrencyHelper currencyHelper;

    public ExpenseServiceImpl(ExpenseRepository expenseRepository, VatUtils vatUtils, UserService userService, ExpenseMapper expenseMapper, CurrencyHelper currencyHelper) {
        this.expenseRepository = expenseRepository;
        this.vatUtils = vatUtils;
        this.userService = userService;
        this.expenseMapper = expenseMapper;
        this.currencyHelper = currencyHelper;
    }

    @Override
    public Expense saveExpense(Expense expense) {
        return this.expenseRepository.save(expense);
    }

    @Override
    public Expense saveExpense(String username, ExpenseRequestDTO expenseRequest) {
        User user = this.userService.getUserByUsername(username);
        Expense expense = this.expenseMapper.toDto(expenseRequest);
        expense.setUser(user);
        // Check if currency is present to do necessary conversion
        if (StringUtils.isEmpty(expenseRequest.getCurrency())) {
            expense.setAmount(this.currencyHelper.convertToPound(expenseRequest.getAmount(),
                    CurrencyConstants.POUND, expenseRequest.getCurrency()));
        }
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
