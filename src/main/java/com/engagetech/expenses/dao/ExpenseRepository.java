package com.engagetech.expenses.dao;

import com.engagetech.expenses.entities.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Haytham DAHRI
 */
@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    @Query(value = "FROM Expense ex where ex.user.username = :username")
    List<Expense> findByUserUsername(@Param("username") String username);

}
