package com.lubycon.ourney.domains.expense.dto;

import lombok.Builder;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

@Getter
public class GetExpenseOneDetailResponse{
    @NotNull
    private Long userId;
    @NotNull
    private String nickName;
    @NotNull
    private String profileImg;
    @NotNull
    private boolean me;
    @NotNull
    private Long price;

    @Builder
    public GetExpenseOneDetailResponse(Long userId, String nickName, String profileImg, Long price){
        this.userId = userId;
        this.nickName = nickName;
        this.profileImg = profileImg;
        this.price = price;
    }
    public void updateMe(){
        this.me = true;
    }
}
