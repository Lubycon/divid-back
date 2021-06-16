package com.lubycon.ourney.domains.expense.dto;

import lombok.Builder;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@Getter
public class CalculateSummaryResponse {
    @NotNull
    private String nickName;
    @Nullable
    private List<CalculateSummaryDetail> detailList;

    @Builder
    public CalculateSummaryResponse(@NotNull String nickName, @Nullable List<CalculateSummaryDetail> detailList) {
        this.nickName = nickName;
        this.detailList = detailList;
    }
}
