package com.lubycon.ourney.domains.expense.dto;

import lombok.Builder;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Getter
public class ExpensePersonalList {
    @NotNull
    private long expenseId;
    @NotNull
    private long payerId;
    @NotNull
    private long userId;
    @NotNull
    private Long price;

    @Builder
    public ExpensePersonalList(long expenseId, long payerId, long userId, @NotNull Long price) {
        this.expenseId = expenseId;
        this.payerId = payerId;
        this.userId = userId;
        this.price = price;
    }
}
