package com.lubycon.ourney.domains.expense.entity;

import com.lubycon.ourney.domains.expense.dto.ExpenseListResponse;
import com.lubycon.ourney.domains.expense.dto.ExpenseListInfoResponse;
import com.lubycon.ourney.domains.expense.dto.GetExpenseOneDetailResponse;
import com.lubycon.ourney.domains.expense.dto.GetExpenseOneResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    @Query("SELECT E.expenseId FROM Expense E WHERE E.title = :title AND E.payDate = :payDate")
    Long findIdByTitleAndPayDate(@Param("title") String title, @Param("payDate") LocalDate payDate);

    @Query("SELECT new com.lubycon.ourney.domains.expense.dto.ExpenseListInfoResponse(E.payDate, SUM(E.totalPrice)) FROM Expense E WHERE E.tripId = :tripId AND E.payDate = :payDate GROUP BY E.payDate")
    ExpenseListInfoResponse getSumPriceByTripIdAndPayDate(@Param("tripId") UUID tripId, @Param("payDate") LocalDate payDate);

    @Query("SELECT new com.lubycon.ourney.domains.expense.dto.ExpenseListResponse(E.expenseId, E.payDate, U.profileImg, U.nickName, E.totalPrice, E.title)" +
            "FROM Expense E INNER JOIN User U ON E.payerId = U.id WHERE E.tripId = :tripId")
    List<ExpenseListResponse> findAllBytripId(@Param("tripId") UUID tripId);

    Expense findExpenseByTripIdAndExpenseId(@Param("tripId") UUID tripId, @Param("expenseId") Long expenseId);

}
