package com.lubycon.ourney.domains.expense.dto;

import lombok.Builder;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.util.List;

@Getter
public class GetExpenseOneResponse {
    @NotNull
    private Long expenseId;
    @NotNull
    private LocalDate payDate;
    @NotNull
    private Long totalPrice;
    @NotNull
    private String title;
    @NotNull
    private Long payerId;
    @NotNull
    private String profileImg;
    @NotNull
    private String nickName;
    @NotNull
    private List<GetExpenseOneDetailResponse> getExpenseOneDetailResponses;

    @Builder
    public GetExpenseOneResponse(Long expenseId, LocalDate payDate, Long totalPrice, String title, Long payerId, String profileImg, String nickName, List<GetExpenseOneDetailResponse> getExpenseOneDetailResponses){
        this.expenseId = expenseId;
        this.payDate = payDate;
        this.totalPrice = totalPrice;
        this.title = title;
        this.payerId = payerId;
        this.profileImg = profileImg;
        this.nickName = nickName;
        this.getExpenseOneDetailResponses = getExpenseOneDetailResponses;
    }
}
