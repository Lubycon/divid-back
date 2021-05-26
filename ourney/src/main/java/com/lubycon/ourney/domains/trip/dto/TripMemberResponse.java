package com.lubycon.ourney.domains.trip.dto;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class TripMemberResponse {
    @NotNull
    private long userId;
    @NotNull
    private String profileImg;
    @NotNull
    private String nickName;
    @NotNull
    private boolean me;

    @Builder
    public TripMemberResponse(@NotNull long userId, @NotNull String profileImg, @NotNull String nickName) {
        this.userId = userId;
        this.profileImg = profileImg;
        this.nickName = nickName;
    }

    public void updateMe() {
        this.me = true;
    }
}
