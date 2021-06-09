package com.lubycon.ourney.domains.expense.dto;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;

@Getter
public class CalcListResponse {
    @NotNull
    private Long giveAmount;
    @NotNull
    private Long takeAmount;


}
