package com.lubycon.divid.domains.expense.dto;

import lombok.Builder;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.util.List;

@Getter
public class GetExpenseResponse {
    @NotNull
    private long expenseId;
    @NotNull
    private LocalDate payDate;
    @NotNull
    private long totalPrice;
    @NotNull
    private String title;
    @NotNull
    private long payerId;
    @NotNull
    private String profileImg;
    @NotNull
    private String nickName;
    @NotNull
    private boolean individual;
    @NotNull
    private List<GetExpenseDetailResponse> getExpenseDetails;

    @Builder
    public GetExpenseResponse(long expenseId, LocalDate payDate, long totalPrice, String title, long payerId, String profileImg, String nickName, boolean individual, List<GetExpenseDetailResponse> getExpenseDetails) {
        this.expenseId = expenseId;
        this.payDate = payDate;
        this.totalPrice = totalPrice;
        this.title = title;
        this.payerId = payerId;
        this.profileImg = profileImg;
        this.nickName = nickName;
        this.individual = individual;
        this.getExpenseDetails = getExpenseDetails;
    }
}
