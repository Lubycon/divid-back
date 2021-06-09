package com.lubycon.ourney.domains.expense.dto;

import lombok.Builder;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

@Getter
public class AmountResponse {
    @NotNull
    private long giveAmount;
    @NotNull
    private long takeAmount;

    @Builder
    public AmountResponse(long giveAmount, long takeAmount){
        this.giveAmount = giveAmount;
        this.takeAmount = takeAmount;
    }
}
