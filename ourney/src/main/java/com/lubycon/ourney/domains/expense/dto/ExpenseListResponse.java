package com.lubycon.ourney.domains.expense.dto;

import lombok.Builder;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;

@Getter
public class ExpenseListResponse {
    @NotNull
    private Long expenseId;
    @NotNull
    private LocalDate payDate;
    @NotNull
    private String profileImg;
    @NotNull
    private String nickName;
    @NotNull
    private Long price;
    @NotNull
    private String title;
    @Builder
    public ExpenseListResponse(Long expenseId, LocalDate payDate, String profileImg, String nickName, Long price, String title){
        this.expenseId = expenseId;
        this.payDate = payDate;
        this.profileImg = profileImg;
        this.nickName = nickName;
        this.price = price;
        this.title = title;
    }
}
