package com.lubycon.divid.domains.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class UserInfoResponse {
    @NotNull
    private long userId;
    @Nullable
    private String nickName;
    @Nullable
    private String profileImg;
    @NotNull
    private boolean me;

    @Builder
    public UserInfoResponse(long userId, String nickName, String profileImg) {
        this.userId = userId;
        this.nickName = nickName;
        this.profileImg = profileImg;
    }

    public void updateMe() {
        this.me = true;
    }


}
