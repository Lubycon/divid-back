package com.lubycon.ourney.domains.expense.dto;

import lombok.Builder;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDate;
import java.util.List;

@Getter
public class ExpenseListInfoResponse {
    @NotNull
    private LocalDate payDate;
    @NotNull
    private Long sumPrice;
    @Nullable
    private List<ExpenseListResponse> detailResponses;

    @Builder
    public ExpenseListInfoResponse(LocalDate payDate, Long sumPrice){
        this.payDate = payDate;
        this.sumPrice = sumPrice;
    }

    public void updateList(List<ExpenseListResponse> detailResponses){
        this.detailResponses = detailResponses;
    }
}
