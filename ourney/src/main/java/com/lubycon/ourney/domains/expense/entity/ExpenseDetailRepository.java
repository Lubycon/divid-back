package com.lubycon.ourney.domains.expense.entity;

import com.lubycon.ourney.domains.expense.dto.GetExpenseDetailResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ExpenseDetailRepository extends JpaRepository<ExpenseDetail, Long> {
    @Query("SELECT new com.lubycon.ourney.domains.expense.dto.GetExpenseDetailResponse(U.id, U.nickName, U.profileImg, E.price) FROM ExpenseDetail E INNER JOIN User U ON E.userId = U.id WHERE E.expenseId = :expenseId")
    List<GetExpenseDetailResponse> findAllByExpenseId(@Param("expenseId") Long expenseId);

    @Query("SELECT d FROM ExpenseDetail d WHERE d.expenseId = :expenseId")
    List<ExpenseDetail> findAllEntityByExpenseId(@Param("expenseId") Long expenseId);

}
