package com.lubycon.divid.domains.expense.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class ExpenseDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long expenseDetailId;

    @Column(name = "expense_id", nullable = false)
    private long expenseId;

    @Column(name = "user_id", nullable = false)
    private long userId;

    @Column(name = "price", nullable = false)
    private long price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "expense_id", nullable = false, insertable = false, updatable = false)
    private Expense expense;

    @Builder
    public ExpenseDetail(long expenseId, long userId, long price) {
        this.expenseId = expenseId;
        this.userId = userId;
        this.price = price;
    }
}
