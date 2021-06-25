package com.lubycon.ourney.domains.expense.dto;

import lombok.Builder;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Getter
public class CalculateListDetail {
    @NotNull
    private String profileImg;
    @NotNull
    private String nickName;
    private long userId;
    private long payerId;
    private long price;
    @Nullable
    private Type type;

    @Builder
    public CalculateListDetail(@NotNull String profileImg, @NotNull String nickName, long userId, long payerId, @NotNull LocalDate payDate, long price) {
        this.profileImg = profileImg;
        this.nickName = nickName;
        this.userId = userId;
        this.payerId = payerId;
        this.price = price;
    }
    public void check(long loginId, long payerId, long id){
        if(payerId == loginId && userId != id){
            this.type = Type.TAKE;
        }
        else if(payerId != loginId && id == loginId){
            this.type = Type.GIVE;
        }
        else{
            this.type = Type.NO;
        }
    }
}
