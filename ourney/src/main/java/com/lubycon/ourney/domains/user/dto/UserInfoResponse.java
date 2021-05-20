package com.lubycon.ourney.domains.user.dto;

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
    private String profile;
    @NotNull
    private boolean me;

    @Builder
    public UserInfoResponse(long userId, String nickName, String profile){
        this.userId = userId;
        this.nickName = nickName;
        this.profile = profile;
    }

    public void updateMe(boolean me){
        this.me = me;
    }


}
