package com.lubycon.ourney.domains.expense.entity;

import com.lubycon.ourney.domains.expense.dto.AmountResponse;
import com.lubycon.ourney.domains.expense.dto.ExpenseListElementResponse;
import com.lubycon.ourney.domains.expense.dto.ExpensePersonalList;
import com.lubycon.ourney.domains.expense.dto.GetExpenseDetailResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface ExpenseDetailRepository extends JpaRepository<ExpenseDetail, Long> {
    @Query("SELECT new com.lubycon.ourney.domains.expense.dto.GetExpenseDetailResponse(U.id, U.nickName, U.profileImg, E.price) FROM ExpenseDetail E INNER JOIN User U ON E.userId = U.id WHERE E.expenseId = :expenseId")
    List<GetExpenseDetailResponse> findAllByExpenseId(@Param("expenseId") Long expenseId);

    @Query("SELECT d FROM ExpenseDetail d WHERE d.expenseId = :expenseId")
    List<ExpenseDetail> findAllEntityByExpenseId(@Param("expenseId") Long expenseId);

    @Query("SELECT new com.lubycon.ourney.domains.expense.dto.ExpenseListElementResponse(e.expenseId, u.id, u.profileImg, u.nickName, e.totalPrice, e.title) FROM User u INNER JOIN Expense e ON u.id = e.payerId WHERE e.tripId = :tripId AND e.payDate = :payDate ORDER BY e.expenseId")
    List<ExpenseListElementResponse> findAllByTripIdAndPayDate(@Param("tripId") UUID tripId, @Param("payDate") LocalDate payDate);

}
