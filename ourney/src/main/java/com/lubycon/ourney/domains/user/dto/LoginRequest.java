package com.lubycon.ourney.domains.user.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;

@Getter
public class LoginRequest {
    @NotNull
    private long kakaoId;
    @Nullable
    private String email;
    @NotNull
    private String nickName;
    @NotNull
    private String profile;
    @NotNull
    private boolean isMember;

    @Builder
    public LoginRequest(long kakaoId, String email, String nickName, String profile, boolean isMember){
        this.kakaoId = kakaoId;
        this.email = email;
        this.nickName = nickName;
        this.profile = profile;
        this.isMember = isMember;
    }
}
