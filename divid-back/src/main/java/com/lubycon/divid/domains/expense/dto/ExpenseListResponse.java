package com.lubycon.divid.domains.expense.dto;

import lombok.Builder;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDate;
import java.util.List;

@Getter
public class ExpenseListResponse {
    @NotNull
    private Long tripTotalPrice;
    @NotNull
    private LocalDate payDate;
    @Nullable
    private List<ExpenseListElementResponse> detailResponses;

    @Builder
    public ExpenseListResponse(@NotNull Long tripTotalPrice, @NotNull LocalDate payDate, @Nullable List<ExpenseListElementResponse> detailResponses) {
        this.tripTotalPrice = tripTotalPrice;
        this.payDate = payDate;
        this.detailResponses = detailResponses;
    }
}
