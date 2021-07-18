package com.lubycon.ourney.domains.user.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;

@Getter
public class LoginRequest {
    private long googleId;
    @NotNull
    private String email;
    @NotNull
    private String name;
    @Nullable
    private String profile;
    @NotNull
    private boolean isMember;

    @Builder
    public LoginRequest(long googleId, String email, String nickName, String profile, boolean isMember){
        this.googleId = googleId;
        this.email = email;
        this.name = nickName;
        this.profile = profile;
        this.isMember = isMember;
    }
}
