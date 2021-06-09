package com.lubycon.ourney.domains.expense.dto;

import lombok.Builder;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

@Getter
public class CalculateListDetail {
    @NotNull
    private String nickName;
    private long price;
    @NotNull
    private boolean give;

    @Builder
    public CalculateListDetail(@NotNull String nickName, long price, boolean give) {
        this.nickName = nickName;
        this.price = price;
        this.give = give;
    }
}
