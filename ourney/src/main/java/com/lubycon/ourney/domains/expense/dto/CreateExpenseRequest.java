package com.lubycon.ourney.domains.expense.dto;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
public class CreateExpenseRequest {
    @NotNull
    private UUID tripId;
    @NotNull
    private LocalDate payDate;
    @NotNull
    private long totalPrice;
    @NotNull
    private String title;
    @NotNull
    private long payerId;
    @NotNull
    private List<CreateExpenseDetailRequest> expenseDetails;
}
