package com.lubycon.ourney.domains.expense.dto;

import lombok.Builder;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDate;
import java.util.List;

@Getter
public class CalculateListDateResponse {
    @NotNull
    private LocalDate payDate;
    @NotNull
    private List<CalculateListResponse> calculateListDetails;

    @Builder
    public CalculateListDateResponse(LocalDate payDate, List<CalculateListResponse> calculateListDetails){
        this.payDate = payDate;
        this.calculateListDetails = calculateListDetails;
    }

}
