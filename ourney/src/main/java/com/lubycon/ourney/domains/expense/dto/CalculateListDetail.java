package com.lubycon.ourney.domains.expense.dto;

import lombok.Builder;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDate;

@Getter
public class CalculateListDetail {
    @NotNull
    private String profileImg;
    @NotNull
    private String nickName;
    private long userId;
    private long payerId;
    @NotNull
    private LocalDate payDate;
    private long price;
    @Nullable
    private Type type;

    @Builder
    public CalculateListDetail(@NotNull String profileImg, @NotNull String nickName, long userId, long payerId, @NotNull LocalDate payDate, long price) {
        this.profileImg = profileImg;
        this.nickName = nickName;
        this.userId = userId;
        this.payerId = payerId;
        this.payDate = payDate;
        this.price = price;
    }
    public void check(long loginId, long payerId, long id){
        if(payerId != loginId && id == loginId){
            type = Type.GIVE;
        }else if(payerId == loginId && userId != loginId){
            type = Type.TAKE;
        }else{
            type = Type.NO;
        }
    }

}
