package com.lubycon.ourney.domains.expense.dto;

import com.lubycon.ourney.domains.user.dto.UserInfoResponse;
import lombok.Builder;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDate;
import java.util.List;

@Getter
public class CalculateListResponse {
    @NotNull
    private long id;
    @NotNull
    private LocalDate payDate;
    private long totalPrice;
    @NotNull
    private String title;
    @NotNull
    private String profileImg;
    @NotNull
    private String nickName;
    @Nullable
    private boolean me;
    @NotNull
    private List<CalculateListDetail> calculateListDetails;

    @Builder
    public CalculateListResponse(long id, LocalDate payDate, long totalPrice, String title, String profileImg, String nickName, List<CalculateListDetail> calculateListDetails){
        this.id = id;
        this.payDate = payDate;
        this.totalPrice = totalPrice;
        this.title = title;
        this.profileImg = profileImg;
        this.nickName = nickName;
        this.calculateListDetails = calculateListDetails;
    }

    public void updateMe(){
        this.me = true;
    }
}
