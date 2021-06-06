package com.lubycon.ourney.domains.expense.dto;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;

@Getter
public class ExpenseDetailRequest {
    @NotNull
    private long userId;
    @NotNull
    private long price;
}
