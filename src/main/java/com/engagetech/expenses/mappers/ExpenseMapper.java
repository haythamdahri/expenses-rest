package com.engagetech.expenses.mappers;

import com.engagetech.expenses.dto.ExpenseRequestDTO;
import com.engagetech.expenses.entities.Expense;
import org.mapstruct.Mapper;

/**
 * @author Haytham DAHRI
 */
@Mapper(componentModel = "spring")
public interface ExpenseMapper extends GenericMapper<ExpenseRequestDTO, Expense> {
}
