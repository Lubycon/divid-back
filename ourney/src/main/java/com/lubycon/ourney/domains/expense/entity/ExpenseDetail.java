package com.lubycon.ourney.domains.expense.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@NoArgsConstructor(access =  AccessLevel.PROTECTED)
@Entity
public class ExpenseDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long expenseDetailId;

    @Column(name = "expense_id", nullable = false)
    private Long expenseId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "price", nullable = false)
    private Long price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "expense_id", nullable = false, insertable=false, updatable=false)
    private Expense expense;

    @Builder
    public ExpenseDetail(Long expenseId, Long userId, Long price){
        this.expenseId = expenseId;
        this.userId = userId;
        this.price = price;
    }
}
