package com.lubycon.divid.domains.expense.dto;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;

@Getter
public class ExpenseDetailRequest {
    @NotNull
    private long userId;
    @NotNull
    private String nickName;
    @NotNull
    private String profileImg;
    @NotNull
    private boolean me;
    @NotNull
    private long price;

    public void updateMe() {
        this.me = true;
    }
}
