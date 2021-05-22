package com.lubycon.ourney.domains.expense.entity;

import com.lubycon.ourney.domains.expense.dto.GetExpenseOneDetailResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface ExpenseDetailRepository extends JpaRepository<ExpenseDetail, Long> {
    @Query("SELECT new com.lubycon.ourney.domains.expense.dto.GetExpenseOneDetailResponse(U.id, U.nickName, U.profileImg, E.price) FROM ExpenseDetail E INNER JOIN User U ON E.userId = U.id WHERE E.expenseId = :expenseId")
    List<GetExpenseOneDetailResponse> findAllByExpenseId(@Param("expenseId") Long expenseId);

    @Query("SELECT d FROM ExpenseDetail d WHERE d.expenseId = :expenseId")
    List<ExpenseDetail> findAllEntityByExpenseId(@Param("expenseId") Long expenseId);

}
