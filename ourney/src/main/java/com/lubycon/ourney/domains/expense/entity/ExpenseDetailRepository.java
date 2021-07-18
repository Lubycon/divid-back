package com.lubycon.ourney.domains.expense.entity;

import com.lubycon.ourney.domains.expense.dto.CalculateListDetail;
import com.lubycon.ourney.domains.expense.dto.CalculateSummaryDetail;
import com.lubycon.ourney.domains.expense.dto.ExpenseListElementResponse;
import com.lubycon.ourney.domains.expense.dto.GetExpenseDetailResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface ExpenseDetailRepository extends JpaRepository<ExpenseDetail, Long> {
    @Query("SELECT new com.lubycon.ourney.domains.expense.dto.GetExpenseDetailResponse(U.id, U.name, U.profileImg, E.price) FROM ExpenseDetail E INNER JOIN User U ON E.userId = U.id WHERE E.expenseId = :expenseId")
    List<GetExpenseDetailResponse> findAllByExpenseId(@Param("expenseId") Long expenseId);

    @Query("SELECT d FROM ExpenseDetail d WHERE d.expenseId = :expenseId")
    List<ExpenseDetail> findAllEntityByExpenseId(@Param("expenseId") Long expenseId);

    @Query("SELECT new com.lubycon.ourney.domains.expense.dto.ExpenseListElementResponse(e.expenseId, u.id, u.profileImg, u.name, e.totalPrice, e.title)" +
            " FROM User u INNER JOIN Expense e ON u.id = e.payerId" +
            " WHERE e.tripId = :tripId AND e.payDate = :payDate" +
            " ORDER BY e.payDate DESC, e.expenseId DESC")
    List<ExpenseListElementResponse> findAllByTripIdAndPayDate(@Param("tripId") UUID tripId, @Param("payDate") LocalDate payDate);

    @Query("SELECT new com.lubycon.ourney.domains.expense.dto.CalculateListDetail(u.profileImg, u.name, u.id, e.payerId, e.payDate, d.price) " +
            "FROM User u, Expense e, ExpenseDetail d" +
            " WHERE u.id = d.userId AND e.expenseId = d.expenseId" +
            " AND e.tripId = :tripId AND e.payDate = :payDate AND d.userId <> e.payerId AND e.expenseId = :expenseId" +
            " ORDER BY e.payDate DESC, e.expenseId DESC, d.expenseId DESC")
    List<CalculateListDetail> findCalculateAllByTripIdAndPayDate(@Param("tripId") UUID tripId, @Param("expenseId") long expenseId, @Param("payDate") LocalDate payDate);

    @Query("SELECT new com.lubycon.ourney.domains.expense.dto.CalculateSummaryDetail(u.profileImg, u.name, u.id, e.payerId, d.price) " +
            "FROM User u, Expense e, ExpenseDetail d" +
            " WHERE u.id = d.userId AND e.expenseId = d.expenseId" +
            " AND e.tripId = :tripId")
    List<CalculateSummaryDetail> findCalculateSummaryByTripId(@Param("tripId") UUID tripId);

}
