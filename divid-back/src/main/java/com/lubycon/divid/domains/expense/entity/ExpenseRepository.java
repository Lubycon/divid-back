package com.lubycon.divid.domains.expense.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.UUID;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    @Query("SELECT E.expenseId FROM Expense E WHERE E.title = :title AND E.payDate = :payDate")
    Long findIdByTitleAndPayDate(@Param("title") String title, @Param("payDate") LocalDate payDate);

    Expense findExpenseByTripIdAndExpenseId(@Param("tripId") UUID tripId, @Param("expenseId") long expenseId);

    @Query("SELECT SUM(e.totalPrice) FROM Expense e WHERE e.tripId = :tripId")
    Long getSumByTripId(@Param("tripId") UUID tripId);

}
