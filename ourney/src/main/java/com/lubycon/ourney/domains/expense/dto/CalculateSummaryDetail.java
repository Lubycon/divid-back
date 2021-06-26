package com.lubycon.ourney.domains.expense.dto;

import lombok.Builder;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Getter
public class CalculateSummaryDetail {
    @NotNull
    private String profileImg;
    @NotNull
    private String nickName;
    private long userId;
    @Nullable
    private Long payerId;
    private long price;
    @Nullable
    private Type type;

    @Builder
    public CalculateSummaryDetail(@NotNull String profileImg, @NotNull String nickName, long userId, long payerId, long price) {
        this.profileImg = profileImg;
        this.nickName = nickName;
        this.userId = userId;
        this.payerId = payerId;
        this.price = price;
        check(price);
    }

    public void check(long price){
        if(price > 0 ){
            this.type = Type.TAKE;
        }
        else if(price < 0){
            this.type = Type.GIVE;
            this.price = Math.abs(price);
        }
        else{
            this.type = Type.NO;
        }
    }
}
