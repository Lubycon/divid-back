package com.lubycon.ourney.domains.expense.dto;

import lombok.Builder;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Getter
public class ExpenseListElementResponse {
    @NotNull
    private long expenseId;
    @NotNull
    private long userId;
    @Nullable
    private String profileImg;
    @NotNull
    private String nickName;
    @NotNull
    private long totalPrice;
    @NotNull
    private String title;
    @NotNull
    private boolean me;

    @Builder
    public ExpenseListElementResponse(@NotNull long expenseId, @NotNull long userId, String profileImg, @NotNull String nickName, @NotNull Long totalPrice, @NotNull String title) {
        this.expenseId = expenseId;
        this.userId = userId;
        this.profileImg = profileImg;
        this.nickName = nickName;
        this.totalPrice = totalPrice;
        this.title = title;
    }

    public void updateMe() {
        this.me = true;
    }


}
