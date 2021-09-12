package com.lubycon.ourney.domains.user.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;

@Getter
public class LoginRequest {
    @NotNull
    private LoginType type;
    private String accessToken;
    private String googleId;
    private String loginId;
    @NotNull
    private String email;
    @NotNull
    private String name;
    @Nullable
    private String profile;
    @NotNull
    private boolean isMember;

    @Builder
    public LoginRequest(String loginId, String email, String nickName, String profile, boolean isMember){
        this.loginId = loginId;
        this.email = email;
        this.name = nickName;
        this.profile = profile;
        this.isMember = isMember;
    }

    public String getLoginId(String googleId){
        if(!googleId.isEmpty()) {
            this.loginId = googleId;
        }
        return loginId;
    }
}
