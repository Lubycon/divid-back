package com.lubycon.ourney.domains.expense.dto;

import com.lubycon.ourney.domains.user.dto.UserInfoResponse;
import lombok.Builder;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.util.List;

@Getter
public class CalculateListResponse {
    @NotNull
    private LocalDate payDate;
    private long totalPrice;
    @NotNull
    private String title;
    @NotNull
    private UserInfoResponse userInfo;
    @NotNull
    private List<CalculateListDetail> calculateListDetails;

    @Builder
    public CalculateListResponse(LocalDate payDate, long totalPrice, String title, UserInfoResponse userInfo, List<CalculateListDetail> calculateListDetails){
        this.payDate = payDate;
        this.totalPrice = totalPrice;
        this.title = title;
        this.userInfo = userInfo;
        this.calculateListDetails = calculateListDetails;
    }
}
