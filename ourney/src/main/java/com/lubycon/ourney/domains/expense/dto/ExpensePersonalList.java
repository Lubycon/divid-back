package com.lubycon.ourney.domains.expense.dto;

import lombok.Builder;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Getter
public class ExpensePersonalList {
    private long expenseId;
    private long payerId;
    private long userId;
    private long price;

    @Builder
    public ExpensePersonalList(long expenseId, long payerId, long userId, long price) {
        this.expenseId = expenseId;
        this.payerId = payerId;
        this.userId = userId;
        this.price = price;
    }
}
