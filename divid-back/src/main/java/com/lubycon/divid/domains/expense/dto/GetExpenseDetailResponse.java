package com.lubycon.divid.domains.expense.dto;

import lombok.Builder;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

@Getter
public class GetExpenseDetailResponse {
    @NotNull
    private long userId;
    @NotNull
    private String nickName;
    @NotNull
    private String profileImg;
    @NotNull
    private boolean me;
    @NotNull
    private long price;

    @Builder
    public GetExpenseDetailResponse(long userId, String nickName, String profileImg, long price) {
        this.userId = userId;
        this.nickName = nickName;
        this.profileImg = profileImg;
        this.price = price;
    }

    public void updateMe() {
        this.me = true;
    }
}
